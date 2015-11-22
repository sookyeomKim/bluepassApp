/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller('adminMypageRegManageScheduleController', adminMypageRegManageScheduleController);

adminMypageRegManageScheduleController.$inject = ['$stateParams', '$mdDialog', 'Action', 'ActionSchedule', 'ActionScheduleByaAction', 'Alert', 'lodash'];

function adminMypageRegManageScheduleController( $stateParams, $mdDialog, Action, ActionSchedule, ActionScheduleByaAction, Alert, lodash) {
    var vm = this;

    vm.mpc = true;
    vm.idBelongToAction = $stateParams.actionId;
    vm.idBelongToClub = $stateParams.id;
    vm.action = {};
    vm.actionScheduleList = [];
    vm.registerSchedule = registerSchedule;
    vm.deleteConfirm = deleteConfirm;

    getAction(vm.idBelongToAction);
    function getAction(id) {
        return Action.get({
            id: id
        }).$promise.then(function (success) {
                return vm.action = success;
            })
    }

    getSchedule(vm.idBelongToAction);
    function getSchedule(id) {
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

    /* 스케줄삭제 */
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
