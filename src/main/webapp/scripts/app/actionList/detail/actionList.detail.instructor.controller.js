/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller('actionListDetailInstructorController', actionListDetailInstructorController);

actionListDetailInstructorController.$inject = ['$scope', 'DataShare', 'InstructorByClub', 'Instructor', 'Alert'];

function actionListDetailInstructorController($scope, DataShare, InstructorByClub, Instructor, Alert) {
    var vm = this;

    vm.showIntroduce = showIntroduce;

    getInstructorByClub();

    function getInstructorByClub() {
        return $scope.$on('data_shared', function () {
            var idBelongToClub = DataShare.getData();

            return InstructorByClub.query({id: idBelongToClub}).$promise.then(function (response) {
                vm.insList = response;
            })
        })
    }

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
