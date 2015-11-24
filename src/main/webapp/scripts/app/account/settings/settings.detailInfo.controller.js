/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller('settingsDetailInfoController', settingsDetailInfoController);

settingsDetailInfoController.$inject = ['$scope', '$timeout', '$element', 'Alert', 'Auth', 'Principal', 'codeNameCommonCode', 'angularLoad', 'lodash'];

function settingsDetailInfoController($scope, $timeout, $element, Alert, Auth, Principal, codeNameCommonCode, angularLoad, lodash) {
    /* 주소입력창을 위한 dom초기화 */
    var $element_wrap;
    $timeout(function () {
        $element_wrap = $element.find("#addrWrap")[0];
    });
    var vm = this;

    vm.settingsAccount = {};
    vm.addressFormOpen = addressFormOpen;
    vm.addressFormClose = addressFormClose;
    vm.selected = [];
    vm.toggle = toggle;
    vm.exists = exists;

    getAccount();
    getExerciseType();

    /*계정정보*/
    function getAccount() {
        return $scope.$on('Account', function (event, response) {
            vm.stateConfirmRegister = lodash.findIndex(response.roles, function (chr) {
                return chr === 'ROLE_REGISTER'
            });
            vm.stateConfirmVendor = lodash.findIndex(response.roles, function (chr) {
                return chr === 'ROLE_VENDOR'
            });
            return vm.settingsAccount = response;
        })
    }

    /* 선호운동 */
    function getExerciseType() {
        return codeNameCommonCode.query({
            codeName: 'CATEGORY_SPORTART'
        }).$promise.then(function (response) {
                vm.commonCodesList = response;
            })
    }

    /* 주소검색 */
    function addressFormOpen() {
        angularLoad.loadScript('http://dmaps.daum.net/map_js_init/postcode.v2.js?autoload=false').then(function () {
            daum.postcode.load(function () {
                // 현재 scroll 위치를 저장해놓는다.
                var currentScroll = Math.max(document.body.scrollTop, document.documentElement.scrollTop);
                new daum.Postcode({
                    oncomplete: function (data) {
                        // 검색결과 항목을 클릭했을때 실행할 코드를 작성하는 부분.

                        // 각 주소의 노출 규칙에 따라 주소를 조합한다.
                        // 내려오는 변수가 값이 없는 경우엔 공백('')값을 가지므로,
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
                                extraAddr += (extraAddr !== '' ? ', ' + data.buildingName : data.buildingName);
                            }
                            // 조합형주소의 유무에 따라 양쪽에 괄호를 추가하여 최종
                            // 주소를 만든다.
                            fullAddr += (extraAddr !== '' ? ' (' + extraAddr + ')' : '');
                        }

                        // 우편번호와 주소 정보를 해당 필드에 넣는다.
                        vm.user.zipcode = data.postcode.replace('-', '');
                        /*
                         * $element.find("#zipcode")[0].value =
                         * data.postcode.replace('-', '');
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

                        // 우편번호 찾기 화면이 보이기 이전으로 scroll 위치를
                        // 되돌린다.
                        document.body.scrollTop = currentScroll;
                    },
                    // 우편번호 찾기 화면 크기가 조정되었을때 실행할 코드를 작성하는
                    // 부분. iframe을 넣은 element의 높이값을 조정한다.
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

    /* 선호지역검색 */
    /*vm.selectedItem = null;
     vm.searchText = "합정동";
     vm.searchTextChange = null;
     vm.querySearch = function querySearch(query) {
     return $http.get("api/zips/search", {
     params: {
     dong: query
     }
     }).then(function (result) {
     return result.data;
     })
     };

     vm.selectedItemChange = function selectedItemChange(item) {
     vm.user.favorSiteId = item.id
     };*/

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

    /* 정보 업데이트 */
    vm.getUpdateAccount = function () {
        var updatePrams = {
            id: vm.settingsAccount.id,
            email: vm.settingsAccount.email,
            gender: vm.settingsAccount.gender,
            name: vm.settingsAccount.name,
            zipcode: vm.settingsAccount.zipcode,
            address1: vm.settingsAccount.address1,
            address2: vm.settingsAccount.address2,
            phoneNumber: vm.settingsAccount.phoneNumber,
            age: vm.settingsAccount.age,
            /*favorSiteId: vm.settingsAccount.favorSiteId,*/
            favorCategoryId: vm.settingsAccount.favorCategoryId,
            exersizeCount: vm.settingsAccount.exersizeCount
        };
        return Auth.updateAccount(updatePrams).then(function () {
            return Principal.identity(true).then(function (account) {
                vm.settingsAccount = account;
                return Alert.alert1('수정되었습니다.')
            })
        }).catch(function () {
            return Alert.alert1('에러가 발생하여 수정되지 않았습니다.')
        });
    };
}
