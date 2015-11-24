/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller('actionListDetailOtherClassController', actionListDetailOtherClassController);

actionListDetailOtherClassController.$inject = [
    '$scope', '$stateParams', '$filter', 'ActionByClub', 'DataShare'
];

function actionListDetailOtherClassController($scope, $stateParams, $filter, ActionByClub, DataShare) {
    var vm = this;
    var currentActionId = $stateParams.id;

    getActionByClub();

    function getActionByClub() {
        return $scope.$on('data_shared', function () {
            /* 클럽ID */
            var idBelongToClub = DataShare.getData();

            return ActionByClub.query({id: idBelongToClub}).$promise.then(function (response) {
                vm.otherActionList = $filter('removeWith')(response, {id: currentActionId});
            });
        })
    }
}
