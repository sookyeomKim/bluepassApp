/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller('adminMypageRegManageScheduleController', adminMypageRegManageScheduleController);

adminMypageRegManageScheduleController.$inject = ['$stateParams', '$mdDialog', 'Action', 'ActionSchedule', 'ActionScheduleByaAction', 'Alert', 'lodash'];

function adminMypageRegManageScheduleController( $stateParams, $mdDialog, Action, ActionSchedule, ActionScheduleByaAction, Alert, lodash) {
    var vm = this;

    /*초기화*/
    vm.mpc = true;
    vm.idBelongToAction = $stateParams.actionId;
    vm.idBelongToClub = $stateParams.id;
    vm.action = {};
    vm.actionScheduleList = [];
    /*스케줄등록*/
    vm.registerSchedule = registerSchedule;
    /* 스케줄삭제 */
    vm.deleteConfirm = deleteConfirm;

    /*클래스정보불러오기*/
    getActionGet(vm.idBelongToAction);
    /*스케줄목록불러오기*/
    getScheduleQuery(vm.idBelongToAction);

    function getActionGet(id) {
        return Action.get({
            id: id
        }).$promise.then(function (success) {
                return vm.action = success;
            })
    }


    function getScheduleQuery(id) {
        return ActionScheduleByaAction.query({
            id: id
        }).$promise.then(function (success) {
                vm.actionScheduleList = success;
                vm.mpc = false;
            })
    }

    function registerSchedule($event, scheduleData) {
        var parentEl = angular.element(document.body);
        $mdDialog.show({
            parent: parentEl,
            targetEvent: $event,
            templateUrl: 'scripts/app/clubAdmin/regManage/schedule/schedule.edit.tmpl.html',
            locals: {
                schedule: scheduleData,
                idBelongToAction: vm.idBelongToAction,
                idBelongToClub: vm.idBelongToClub
            },
            clickOutsideToClose: true,
            controller: scheduleEditController,
            controllerAs: "vm"
        })
    }

    function deleteConfirm($event, id) {
        return Alert.alert2($event, '삭제', '정말 삭제하시겠습니까?', '삭제하기').then(function () {
            return ActionSchedule.delete({id: id}).$promise.then(function () {
                var delIndex = lodash.findIndex(vm.actionScheduleList, {id: id});
                vm.actionScheduleList.splice(delIndex, 1);
                return Alert.alert1('삭제되었습니다.')
            }).catch(function () {
                return Alert.alert1('에러가 발생하여 삭제되지 않았습니다.')
            })
        });
    }
}
