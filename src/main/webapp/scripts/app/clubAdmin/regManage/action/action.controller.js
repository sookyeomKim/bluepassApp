/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller(
    'adminMypageRegManageActionController',
    ['$scope', '$log', '$timeout', '$stateParams', 'Action', 'ActionByClub', 'DeleteImgByAction', 'Alert', 'lodash',
        function ($scope, $log, $timeout, $stateParams, Action, ActionByClub, DeleteImgByAction, Alert, lodash) {
            $scope.mpc = true;

            /* 클럽ID */
            var idBelongToClub = $scope.idBelongToClub = $stateParams.id;

            /* 클래스 CRUD */
            $scope.loadAll = function () {
                var actions = ActionByClub.query({
                    id: idBelongToClub
                });
                actions.$promise.then(function (success) {
                    $scope.actionList = success;
                    $scope.mpc = false;
                });
            };

            $scope.loadAll();

            $scope.reset = function () {
                $scope.actionList = [];
                $scope.mpc = true;
                $scope.loadAll();
            };

            /* 액션삭제 */
            $scope.deleteConfirm = function ($event, id) {
                return Alert.alert2($event, '삭제', '정말 삭제하시겠습니까?', '삭제하기').then(function () {
                    return Action.delete({id: id}).$promise.then(function () {
                        var delIndex = lodash.findIndex($scope.actionList, {id: id});
                        $scope.actionList.splice(delIndex, 1);
                        return Alert.alert1('삭제되었습니다.')
                    }).catch(function () {
                        return Alert.alert1('에러가 발생하여 삭제되지 않았습니다.')
                    })
                });
            };

            /* 이미지삭제 */
            $scope.imgDeleteConfirm = function ($event, id, imageId) {
                return Alert.alert2($event, '이미지삭제', '정말 삭제하시겠습니까?', '삭제하기').then(function () {
                    return DeleteImgByAction.imgDelete({
                        id: id,
                        imageId: imageId
                    }).$promise.then(function () {
                            var findIndex = lodash.findIndex($scope.actionList, {id: id});
                            var delIndex = lodash.findIndex($scope.actionList[findIndex].images, {id: imageId});
                            $scope.actionList[findIndex].images.splice(delIndex, 1);

                        }).catch(function () {
                            return Alert.alert1('에러가 발생하여 삭제되지 않았습니다.')
                        })
                })
            };
        }]);
