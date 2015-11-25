/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller('scheduleEditController', scheduleEditController);

scheduleEditController.$inject = [
    '$state',
    '$filter',
    '$mdDialog',
    'ActionSchedule',
    'InstructorByClub',
    'lodash',
    'schedule',
    'idBelongToAction',
    'idBelongToClub',
    'Alert'
];

function scheduleEditController($state, $filter, $mdDialog, ActionSchedule,
                                InstructorByClub, lodash, schedule, idBelongToAction, idBelongToClub, Alert) {
    var vm = this;

    vm.actionSchedule = {
        day: null,
        attendeeLimit: null,
        startTime: new Date(),
        endTime: new Date(),
        startDate: new Date(),
        endDate: new Date(),
        scheduleType: '기간지정',
        actionId: idBelongToAction,
        instructorId: null
    };
    vm.days = [];
    vm.dayArry = ['일', '월', '화', '수', '목', '금', '토'];
    vm.toggle = toggle;
    vm.exists = exists;
    vm.disabled = disabled;
    vm.minDate = vm.minDate ? null : new Date();
    vm.dateOptions = {
        showWeeks: false,
        startingDay: 1
    };
    vm.status = {
        opened: false
    };
    vm.open = {
        date1: false,
        date2: false
    };
    vm.openCalendar = openCalendar;
    vm.cancel = cancel;
    vm.getActionSchedule = getActionSchedule;

    /*강사정보불러오기*/
    getInstructorQuery();
    /*스케줄정보불러오기*/
    if (schedule) {
        getSchedule(schedule.id);
    }

    function getInstructorQuery() {
        return InstructorByClub.query({id: idBelongToClub}).$promise.then(function (response) {
            vm.instructors = response;
        })
    }

    function getSchedule(id) {
        return ActionSchedule.get({
            id: id
        }).$promise.then(function (success) {
                var startTime = new Date();
                var endTime = new Date();
                vm.actionSchedule = {
                    id: success.id,
                    actionId: success.action.id,
                    attendeeLimit: success.attendeeLimit,
                    startDate: success.startDate,
                    endDate: success.endDate,
                    scheduleType: success.scheduleType
                };
                vm.actionSchedule.instructorId = success.instructor.id;
                startTime.setHours($filter("date")(success.startTime, "HH"));
                startTime.setMinutes($filter("date")(success.startTime, "mm"));
                endTime.setHours($filter("date")(success.endTime, "HH"));
                endTime.setMinutes($filter("date")(success.endTime, "mm"));
                vm.actionSchedule.startTime = startTime;
                vm.actionSchedule.endTime = endTime;
                vm.days = lodash.pull(lodash.toArray(success.day), ',');
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

    function disabled(date, mode) {
        return ( mode === 'day' && ( date.getDay() === 0 || date.getDay() === 6 ) );
    }

    function openCalendar(e, date) {
        e.preventDefault();
        e.stopPropagation();

        vm.open[date] = true;
    }

    function cancel() {
        $mdDialog.cancel();
    }

    function getActionSchedule() {
        vm.actionSchedule.day = lodash(vm.days).toString();
        vm.actionSchedule.startTime = $filter("date")(vm.actionSchedule.startTime,
            "yyyy-MM-ddTHH:mm:00.000Z", "KST");
        vm.actionSchedule.endTime = $filter("date")(vm.actionSchedule.endTime,
            "yyyy-MM-ddTHH:mm:00.000Z", "KST");
        vm.actionSchedule.startDate = $filter("date")(vm.actionSchedule.startDate,
            "yyyy-MM-ddT00:00:00.000Z", "KST");
        vm.actionSchedule.endDate = $filter("date")(vm.actionSchedule.endDate,
            "yyyy-MM-ddT00:00:00.000Z", "KST");
        if (schedule) {
            getActionScheduleUpdate().then(getSuccess).catch(getError);
        } else {
            getActionScheduleSave().then(getSuccess).catch(getError);
        }

        function getActionScheduleUpdate() {
            return ActionSchedule.update(vm.actionSchedule).$promise
        }

        function getActionScheduleSave() {
            return ActionSchedule.save(vm.actionSchedule).$promise
        }

        function getSuccess() {
            cancel();
            $state.go($state.current, {}, {reload: true});
        }

        function getError() {
            Alert.alert1('등록에 실패하였습니다. 입력한 정보를 다시 확인해주세요.');
        }
    }
}
