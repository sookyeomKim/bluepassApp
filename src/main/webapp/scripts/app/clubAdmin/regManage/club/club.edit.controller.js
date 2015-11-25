/**
 * 만든이 : 수겨미
 */
'use strict';

angular
    .module('bluepassApp')
    .controller('adminMypageRegManageEditController', adminMypageRegManageEditController);

adminMypageRegManageEditController.$inject = [
    '$timeout',
    '$state',
    '$stateParams',
    '$element',
    'Club',
    'CodeNameCommonCode',
    'angularLoad',
    'Alert'
];

function adminMypageRegManageEditController($timeout, $state, $stateParams, $element, Club, CodeNameCommonCode,
                                            angularLoad, Alert) {
    var vm = this;
    /* 주소입력창을 위한 dom초기화 */
    var $element_wrap;
    $timeout(function () {
        $element_wrap = $element.find("#addrWrap")[0];
    }, 0);

    /*유의사항초기화*/
    vm.features = [];
    /*클럽파라미터초기화*/
    vm.club = {
        id: null,
        name: null,
        description: null,
        licenseNumber: null,
        phoneNumber: null,
        homepage: null,
        zipcode: null,
        address1: null,
        address2: null,
        onlyFemale: false,
        addressSimple: null,
        managerMobile: null,
        features: [],
        notificationType: 0,
        reservationClose: 1,
        oldAddress: null,
        category: {
            id: null
        }
    };
    /* 유의사항 */
    vm.feature1 = CodeNameCommonCode.query({
        codeName: 'FEATURE_001'
    });
    vm.feature2 = CodeNameCommonCode.query({
        codeName: 'FEATURE_002'
    });
    vm.feature3 = CodeNameCommonCode.query({
        codeName: 'FEATURE_003'
    });
    vm.feature4 = CodeNameCommonCode.query({
        codeName: 'FEATURE_004'
    });
    /*유의사항 갱신*/
    vm.toggle = toggle;
    /*선택됨 표시*/
    vm.exists = exists;
    /*주소검색창열기*/
    vm.addressFormOpen = addressFormOpen;
    /*주소검색창닫기*/
    vm.addressFormClose = addressFormClose;
    /*클럽등록*/
    vm.getClub = getClub;

    /*클럽정보불러오기*/
    if ($stateParams.id) {
        getClubGet($stateParams.id);
    }

    function getClubGet(id) {
        return Club.get({id: id}).$promise.then(function (response) {
            vm.club = response;
            for (var i = 0; i < vm.club.features.length; i++) {
                vm.features.push(vm.club.features[i].id);
            }
            if (vm.club.category === null) {
                vm.club.category = {
                    id: null
                };
            }
        })
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

    function addressFormOpen() {
        angularLoad
            .loadScript('http://dmaps.daum.net/map_js_init/postcode.v2.js?autoload=false')
            .then(
            function () {
                daum.postcode
                    .load(function () {
                        // 현재 scroll 위치를
                        // 저장해놓는다.
                        var currentScroll = Math.max(document.body.scrollTop,
                            document.documentElement.scrollTop);
                        new daum.Postcode(
                            {
                                oncomplete: function (data) {
                                    var fullAddr = data.address;
                                    var extraAddr = '';
                                    if (data.addressType === 'R') {
                                        if (data.bname !== '') {
                                            extraAddr += data.bname;
                                        }
                                        if (data.buildingName !== '') {
                                            extraAddr += (extraAddr !== '' ? ', '
                                            + data.buildingName
                                                : data.buildingName);
                                        }
                                        fullAddr += (extraAddr !== '' ? ' ('
                                        + extraAddr + ')'
                                            : '');
                                    }
                                    vm.club.zipcode = data.zonecode;
                                    vm.club.address1 = fullAddr;
                                    vm.club.oldAddress = data.sido
                                        + data.sigungu + data.bname;
                                    $element_wrap.style.display = 'none';

                                    $element.find("#address1")[0].focus();
                                    $element.find("#address2")[0].focus();
                                    document.body.scrollTop = currentScroll;
                                },
                                onresize: function (size) {
                                    $element_wrap.style.height = size.height
                                        + "px";
                                },
                                width: '100%',
                                height: '100%'
                            }).embed($element_wrap);
                        $element_wrap.style.display = 'block';
                    });
            }).catch(function () {
                alert("일시적인 오류가 발생하였습니다. 회사에 문의해주세요!");
            });
    }

    function addressFormClose() {
        $element_wrap.style.display = 'none';
    }

    function getClub() {
        vm.club.features = vm.features;
        if (vm.club.licenseNumber === '') {
            vm.club.licenseNumber = null;
        }
        if (vm.club.category.id === null) {
            vm.club.category = null;
        }
        if (vm.club.id != null) {
            return getClubUpdate().then(getSuccess).catch(getError)
        } else {
            return getClubSave().then(getSuccess).catch(getError)
        }

        function getClubSave() {
            return Club.save(vm.club).$promise
        }

        function getClubUpdate() {
            return Club.update(vm.club).$promise
        }

        function getSuccess() {
            return $state.go("adminRegManage", {
                id: vm.idBelongToClub
            })
        }

        function getError() {
            return Alert.alert1('에러발생')
        }
    }
}
