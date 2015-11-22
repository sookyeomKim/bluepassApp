/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller(
    'actionListDetailInstructorController',
    ['$scope', 'DataShare', 'InstructorByClub', 'Instructor',
        function ($scope, DataShare, InstructorByClub, Instructor) {
            $scope.$on('data_shared', function () {
                /* 클럽ID */
                var idBelongToClub = DataShare.getData();
                /* 강사 CRUD */
                $scope.loadAll = function () {
                    var insCall = InstructorByClub.query({
                        id: idBelongToClub
                    });
                    insCall.$promise.then(function (success) {
                        $scope.insList = success;
                    })
                };
                $scope.loadAll();

                $scope.showIntroduce = function (id) {
                    return Instructor.get({
                        id: id
                    }).$promise.then(function (response) {
                            return Alert.alert1(response.description, '강사소개')
                        }).catch(function () {
                            return Alert.alert1('강사정보를 불러오지 못 하였습니다.')
                        })
                }
            });
        }]);
