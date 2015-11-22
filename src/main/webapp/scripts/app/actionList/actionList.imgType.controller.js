/**
 * 만든이 :수겨미
 */
'use strict';

angular.module('bluepassApp').controller('actionListImgTypeController',
    ['$scope', '$log', 'Action', 'ParseLinks', function ($scope, $log, Action, ParseLinks) {
        /* 스피너초기화 */
        $scope.mpc = true;

        /* 클래스리퀘스트 */
        $scope.loadAll = function (exerciseType, siteType) {
            var actions = Action.query({
                page: $scope.page,
                per_page: 6,
                category: exerciseType,
                address: siteType
            }, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
            });
            actions.$promise.then(function (success) {
                /*console.log(success)*/
                for (var i = 0; i < success.length; i++) {
                    $scope.actionList.push(success[i]);
                }
                $scope.mpc = false;
                /* console.log($scope.actionList) */
            });
        };

        /* 인피니티스크롤 로드 */
        $scope.loadPage = function (page, exerciseType, siteType) {
            $scope.mpc = true;
            $scope.page = page;
            $scope.loadAll(exerciseType, siteType);
        };

        /* 검색와처 */
        $scope.$watchGroup(['exerciseType', 'siteType'], function (newVal) {
            $scope.page = 1;
            $scope.actionList = [];
            $scope.loadAll(newVal[0], newVal[1]);
        });
    }]);
