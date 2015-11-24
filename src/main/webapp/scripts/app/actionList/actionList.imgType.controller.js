/**
 * 만든이 :수겨미
 */
'use strict';

angular.module('bluepassApp').controller('actionListImgTypeController', actionListImgTypeController);

actionListImgTypeController.$inject = ['$scope', 'Action', 'ParseLinks'];

function actionListImgTypeController($scope, Action, ParseLinks) {
    var vm = this;

    vm.mpc = true;
    vm.queryPrams = {};
    vm.loadPage = loadPage;

    getQueryPrams();

    function getQueryPrams() {
        return $scope.$on('QueryPrams', function (event, response) {
            vm.page = 1;
            vm.actionList = [];
            vm.queryPrams = response;
            return getAction(vm.queryPrams);
        })
    }

    /*인피니티스크롤 로드*/
    function loadPage(page, queryPrams) {
        vm.mpc = true;
        vm.page = page;
        return getAction(queryPrams)
    }

    function getAction(queryPrams) {
        return Action.query({
            page: vm.page,
            per_page: 6,
            category: queryPrams.exerciseType,
            address: queryPrams.siteType
        }, function (result, headers) {
            vm.links = ParseLinks.parse(headers('link'));
        }).$promise.then(function (response) {
                for (var i = 0; i < response.length; i++) {
                    vm.actionList.push(response[i]);
                }
                vm.mpc = false;
            })
    }
}
