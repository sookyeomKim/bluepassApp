/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller(
    'adminMypageRegManageController',
    ['$scope', '$log', '$timeout', 'partnerClubs', 'Club',
        function ($scope, $log, $timeout, partnerClubs, Club) {

            $scope.mpc = true;

            /* 업체/그룹 call */
            $scope.loadAll = function () {
                var clubs = partnerClubs.query({
                    page: -1
                });
                clubs.$promise.then(function (success) {
                    $scope.clubList = success;
                    $scope.mpc = false;
                    /*console.log($scope.clubList)*/
                }).then(function () {
                    jQuery("#club-accordion .panel-heading a").click(function (e) {
                        e.preventDefault();
                    });
                });
            };

            $scope.loadAll();

            $scope.reset = function () {
                $scope.mpc = true;
                $scope.loadAll();
            };

            /* 클럽삭제 */
            $scope.clubDelete = function (id) {
                Club.get({
                    id: id
                }, function (result) {
                    $scope.club = result;
                    $('#deleteClubConfirmation').modal('show');
                });
            };

            $scope.confirmDelete = function (id) {
                Club._delete({
                    id: id
                }, function () {
                    $scope.reset();
                    $('#deleteClubConfirmation').modal('hide');
                });
            };
        }]);
