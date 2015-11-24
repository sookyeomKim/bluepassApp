/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller('actionListDetailController', actionListDetailController);

actionListDetailController.$inject = [
    '$rootScope',
    '$state',
    '$log',
    '$timeout',
    '$stateParams',
    'Action',
    'DataShare',
    'RandomValue',
    'authorize'
];

function actionListDetailController($rootScope, $state, $log, $timeout, $stateParams, Action, DataShare,
                                    RandomValue, authorize) {
    var vm = this;

    /*계정정보*/
    vm.account = authorize;
    /* 이전버튼 */
    vm.toPrevious = toPrevious;
    /* breadcrumbs이미지 로테이션 */
    vm.selectImg = RandomValue.randomCount(6);
    /* 상세설명&강사정보 탭 */
    vm.tabSelected = 1;

    getAction($stateParams.id);

    function getAction(id) {
        return Action.get({id: id}).$promise.then(function (success) {
            vm.actionDetail = success;
            vm.classImgList = vm.actionDetail.images;
            return DataShare.sendData(vm.actionDetail.club.id);
        }, function (error) {
            $log.debug(error);
        }).then(function () {
            $timeout(function () {
                var slider = new MasterSlider();
                slider.control('arrows');
                slider.setup('masterslider', {
                    width: 600,
                    height: 400,
                    space: 5,
                    view: 'fade',
                    autoplay: true
                });
            });
        })
    }

    function toPrevious() {
        $state.go($rootScope.previousStateName, $rootScope.previousStateParams, {
            reload: true
        });
    }
}
