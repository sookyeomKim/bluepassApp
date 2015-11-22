/**
 * 만든이 : 수겨미
 */
'use strict';
angular.module('bluepassApp').controller(
    'adminMypageRegManageInstructorController',
    ['$scope', '$log', '$timeout', '$stateParams', 'Instructor', 'InstructorByClub', 'Alert', 'lodash',
        function ($scope, $log, $timeout, $stateParams, Instructor, InstructorByClub, Alert, lodash) {
            $scope.mpc = true;

            /* 클럽ID */
            $scope.idBelongToClub = $stateParams.id;

            /* 강사 CRUD */
            $scope.loadAll = function () {
                var instructors = InstructorByClub.query({
                    id: $scope.idBelongToClub
                });
                instructors.$promise.then(function (success) {
                    $scope.instructorList = success;
                    $scope.mpc = false;
                });
            };

            $scope.loadAll();

            $scope.reset = function () {
                $scope.instructorList = [];
                $scope.mpc = true;
                $scope.loadAll();
            };

            /* 강사삭제 */
            $scope.deleteConfirm = function ($event, id) {
                return Alert.alert2($event, '삭제', '정말 삭제하시겠습니까?', '삭제하기').then(function () {
                    return Instructor.delete({id: id}).$promise.then(function () {
                        var delIndex = lodash.findIndex($scope.instructorList, {id: id});
                        $scope.instructorList.splice(delIndex, 1);
                        return Alert.alert1('삭제되었습니다.')
                    }).catch(function () {
                        return Alert.alert1('에러가 발생하여 삭제되지 않았습니다.')
                    })
                });
            };

            /* 강사소개보기 */
            $scope.showIntroduce = function ($event, id) {
                return Instructor.get({
                    id: id
                }).$promise.then(function (response) {
                        return Alert.alert1(response.description, '강사소개')
                    }).catch(function () {
                        return Alert.alert1('강사정보를 불러오지 못 하였습니다.')
                    })
            }
        }]);
