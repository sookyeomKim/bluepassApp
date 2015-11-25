/**
 * 만든이 : 수겨미
 */
'use strict';
angular.module('bluepassApp').controller('adminMypageRegManageInstructorController', adminMypageRegManageInstructorController);

adminMypageRegManageInstructorController.$inject = [
    '$stateParams', 'Instructor', 'InstructorByClub', 'Alert', 'lodash'
];

function adminMypageRegManageInstructorController($stateParams, Instructor, InstructorByClub, Alert, lodash) {
    var vm = this;

    vm.mpc = true;
    /* 클럽ID */
    vm.idBelongToClub = $stateParams.id;
    /*강사목록초기화*/
    vm.instructorList = [];
    /*강사삭제*/
    vm.deleteConfirm = deleteConfirm;
    /*강사소개보기*/
    vm.showIntroduce = showIntroduce;

    /*강사목록 불러오기*/
    getInstructorByClubQuery();

    function getInstructorByClubQuery() {
        return InstructorByClub.query({id: vm.idBelongToClub}).$promise.then(function (response) {
            vm.instructorList = response;
            vm.mpc = false;
        })
    }

    /* 강사삭제 */
    function deleteConfirm($event, id) {
        return Alert.alert2($event, '삭제', '정말 삭제하시겠습니까?', '삭제하기').then(function () {
            return Instructor.delete({id: id}).$promise.then(function () {
                var delIndex = lodash.findIndex(vm.instructorList, {id: id});
                vm.instructorList.splice(delIndex, 1);
                return Alert.alert1('삭제되었습니다.')
            }).catch(function () {
                return Alert.alert1('에러가 발생하여 삭제되지 않았습니다.')
            })
        });
    }

    /* 강사소개보기 */
    function showIntroduce(id) {
        return Instructor.get({
            id: id
        }).$promise.then(function (response) {
                return Alert.alert1(response.description, '강사소개')
            }).catch(function () {
                return Alert.alert1('강사정보를 불러오지 못 하였습니다.')
            })
    }
}
