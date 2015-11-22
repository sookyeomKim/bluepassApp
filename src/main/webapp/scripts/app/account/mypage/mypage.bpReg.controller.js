/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller('mypageBpRegController', mypageBpRegController);

mypageBpRegController.$inject = [
    '$scope',
    '$timeout',
    '$state',
    '$element',
    'WizardHandler',
    'codeNameCommonCode',
    'Auth',
    'Principal',
    'angularLoad'
];

function mypageBpRegController($scope, $timeout, $state, $element, WizardHandler,
                               codeNameCommonCode, Auth, Principal, angularLoad) {
    var vm = this;
    var $element_wrap;
    $timeout(function () {
        $element_wrap = $element.find("#addrWrap")[0];
    });

    vm.user = {
        id: null,
        name: null,
        zipcode: null,
        address1: null,
        address2: null,
        age: null,
        gender: "F",
        phoneNumber: null,
        favorSiteId: null,
        favorCategoryId: null,
        exersizeCount: null,
        ticketId: 16
    };
    vm.wizardLegend = '회원권선택';
    vm.changeFromFirstToSecond = changeFromFirstToSecond;
    vm.changeFromSecondToThird = changeFromSecondToThird;
    vm.changeFromSecondToFirst = changeFromSecondToFirst;
    vm.changeFromThirdToSecond = changeFromThirdToSecond;
    vm.finishedWizard = finishedWizard;
    vm.addressFormOpen = addressFormOpen;
    vm.addressFormClose = addressFormClose;
    vm.ticketList = [];
    vm.exerciseList = [];
    vm.selected = [];
    vm.toggle = toggle;
    vm.exists = exists;
    vm.paymentWay = 0;
    vm.selectedPw = selectedPw;

    getAccount();
    function getAccount() {
        return Principal.identity().then(function (reponse) {
            getTickets();
            getExsercise();
            return vm.user.id = reponse.id;
        })
    }

    function changeFromFirstToSecond() {
        $scope.wizardLegend = '추가정보입력';
        WizardHandler.wizard().next();
    }

    function changeFromSecondToThird() {
        $scope.wizardLegend = '결제방법선택';
        WizardHandler.wizard().next();
    }

    function changeFromSecondToFirst() {
        $scope.wizardLegend = '회원권선택';
        WizardHandler.wizard().previous();
    }

    function changeFromThirdToSecond() {
        $scope.wizardLegend = '추가정보입력';
        WizardHandler.wizard().previous();
    }

    function addressFormOpen() {
        angularLoad.loadScript('http://dmaps.daum.net/map_js_init/postcode.v2.js?autoload=false').then(
            function () {
                daum.postcode.load(function () {
                    // 현재 scroll 위치를 저장해놓는다.
                    var currentScroll = Math.max(document.body.scrollTop,
                        document.documentElement.scrollTop);
                    new daum.Postcode({
                        oncomplete: function (data) {
                            // 검색결과 항목을 클릭했을때 실행할 코드를 작성하는
                            // 부분.

                            // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                            // 내려오는 변수가 값이 없는 경우엔 공백('')값을
                            // 가지므로,
                            // 이를 참고하여 분기 한다.
                            var fullAddr = data.address; // 최종
                            // 주소
                            // 변수
                            var extraAddr = ''; // 조합형 주소 변수

                            // 기본 주소가 도로명 타입일때 조합한다.
                            if (data.addressType === 'R') {
                                // 법정동명이 있을 경우 추가한다.
                                if (data.bname !== '') {
                                    extraAddr += data.bname;
                                }
                                // 건물명이 있을 경우 추가한다.
                                if (data.buildingName !== '') {
                                    extraAddr += (extraAddr !== '' ? ', ' + data.buildingName
                                        : data.buildingName);
                                }
                                // 조합형주소의 유무에 따라 양쪽에 괄호를
                                // 추가하여 최종
                                // 주소를 만든다.
                                fullAddr += (extraAddr !== '' ? ' (' + extraAddr + ')' : '');
                            }

                            // 우편번호와 주소 정보를 해당 필드에 넣는다.
                            vm.user.zipcode = data.postcode.replace('-', '');
                            /*
                             * $element.find("#zipcode")[0].value =
                             * data.postcode.replace('-',
                             * '');
                             */
                            vm.user.address1 = fullAddr;
                            /*
                             * $element.find("#address1")[0].value =
                             * fullAddr;
                             */

                            // iframe을 넣은 element를 안보이게 한다.
                            $element_wrap.style.display = 'none';

                            $element.find("#address1")[0].focus();
                            $element.find("#address2")[0].focus();

                            // 우편번호 찾기 화면이 보이기 이전으로 scroll
                            // 위치를
                            // 되돌린다.
                            document.body.scrollTop = currentScroll;
                        },
                        // 우편번호 찾기 화면 크기가 조정되었을때 실행할 코드를
                        // 작성하는
                        // 부분. iframe을 넣은 element의 높이값을
                        // 조정한다.
                        onresize: function (size) {
                            $element_wrap.style.height = size.height + "px";
                        },
                        width: '100%',
                        height: '100%'
                    }).embed($element_wrap);

                    // iframe을 넣은 element를 보이게 한다.
                    $element_wrap.style.display = 'block';
                });
            }).catch(function () {
                alert("일시적인 오류가 발생하였습니다. 회사에 문의해주세요!");
            });
    }

    function addressFormClose() {
        // iframe을 넣은 element를 안보이게 한다.
        $element_wrap.style.display = 'none';
    }

    /*/!* 선호지역검색 *!/
     $scope.selectedItem = null;
     $scope.searchText = null;
     $scope.searchTextChange = null;
     $scope.querySearch = function (query) {
     return $http.get("api/zips/search", {
     params: {
     dong: query
     }
     }).then(function (result) {
     return result.data;
     });
     };

     $scope.selectedItemChange = function (item) {
     if (item.id) {
     $scope.user.favorSiteId = item.id
     }
     };*/

    function getTickets() {
        return codeNameCommonCode.query({codeName: 'CATEGORY_TICKET'}).$promise.then(function (success) {
            return vm.ticketList = success;
        });
    }

    function getExsercise() {
        return codeNameCommonCode.query({codeName: 'CATEGORY_SPORTART'}).$promise.then(function (success) {
            return vm.exerciseList = success;
        });
    }

    function toggle(item, list) {
        var idx = list.indexOf(item);
        if (idx > -1)
            list.splice(idx, 1);
        else
            list.push(item);
    }

    function exists(item, list) {
        return list.indexOf(item) > -1;
    }

    function selectedPw(n) {
        vm.paymentWay = n;
    }

    function finishedWizard() {
        if (vm.paymentWay === 0) {
            Auth.addAccount(vm.user).then(function () {
                $state.go("bpStandby", {
                    paymentWay: vm.paymentWay
                }, {
                    reload: true
                });
            }, function () {
                alert("에러가 발생하였습니다.")
            });
        } else {
            alert("진행할 수 없는 방식입니다.")
        }
    }

    $scope.$on('$stateChangeStart', function () {
        jQuery("body").removeClass("bpRegBackground").removeClass("imgBackgroundOn");
    });
    $scope.$on('$stateChangeSuccess', function () {
        jQuery("body").addClass("bpRegBackground").addClass("imgBackgroundOn");
    });
}
