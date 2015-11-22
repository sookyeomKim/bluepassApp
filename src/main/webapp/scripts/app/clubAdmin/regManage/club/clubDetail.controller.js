/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller('adminMypageRegManageDetailController',
    ['$scope', '$stateParams', 'Club', function ($scope, $stateParams, Club) {
        /* 클럽 정보 콜 */
        $scope.load = function (id) {
            Club.get({
                id: id
            }, function (result) {
                $scope.club = result;
            });
        };
        $scope.load($stateParams.id);

        /* 탭 */
        $scope.tabSelected = 1;
    }]);
