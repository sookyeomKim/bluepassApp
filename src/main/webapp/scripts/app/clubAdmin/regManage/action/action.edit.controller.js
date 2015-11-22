/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller(
    'adminMypageRegManageActionEditController',
    [
        '$scope',
        '$log',
        '$timeout',
        '$stateParams',
        '$state',
        'Action',
        'ActionByClub',
        'FileUploader',
        'codeNameCommonCode',
        'DeleteImgByAction',
        function ($scope, $log, $timeout, $stateParams, $state, Action, ActionByClub, FileUploader,
                  codeNameCommonCode, DeleteImgByAction) {
            /* 클럽ID */
            var idBelongToClub = $scope.idBelongToClub = $stateParams.id;

            /* 초기화 */
            $scope.clear = function () {
                $scope.count = 0;
                $scope.photosId = [];
                $scope.action = {
                    id: null,
                    title: null,
                    description: null,
                    shortDescription: null,
                    useLimitType: '무제한',
                    useLimitValue: null,
                    category: null,
                    movieIds: null,
                    images: [],
                    imagesIds: []
                };
            };
            $scope.clear();

            /* 수정정보 불러오기 */
            $scope.actionInfoUpdate = function (id) {
                Action.get({
                    id: id
                }, function (result) {
                    $scope.action = result;
                });
            };
            if ($stateParams.actionId) {
                $scope.actionInfoUpdate($stateParams.actionId)
            }

            /* 카테고리 */
            var commonCodes = codeNameCommonCode.query({
                codeName: 'CATEGORY_SPORTART'
            });
            commonCodes.$promise.then(function (success) {
                $scope.commonCodesList = success;
            });

            /* 저장 */
            $scope.save = function () {
                if ($scope.action.id != null) {
                    var actionUpdate = Action.update($scope.action);
                    actionUpdate.$promise.then(function (success) {
                        if (success) {
                            $state.go("adminRegManage.Action", {
                                id: idBelongToClub
                            })
                        } else {
                            $scope.alert = "에러발생";
                            $("#confirmationModal").modal("show");
                        }
                    });
                } else {
                    var actionSave = Action.save($scope.action);
                    actionSave.$promise.then(function (success) {
                        if (success) {
                            $state.go("adminRegManage.Action", {
                                id: idBelongToClub
                            })
                        } else {
                            $scope.alert = "에러발생";
                            $("#confirmationModal").modal("show");
                        }
                    });
                }
            };

            /* 파일업로더 */
            var actionUploader = $scope.actionUploader = new FileUploader({
                url: '/image/upload',
                queueLimit: 30,
                autoUpload: true
            });

            actionUploader.onSuccessItem = function (fileItem, response, status, headers) {
                $scope.photoId = response.files[0].id;
                if (!actionUploader.queue[$scope.count].isError) {
                    actionUploader.queue[$scope.count].index = response.files[0].id;
                }
                $scope.count++;
                $scope.photosId.push(response.files[0].id);
            };

            actionUploader.onErrorItem = function (item, response, status, headers) {
                $scope.count++;
            };

            $scope.imgUpload = function (index) {
                actionUploader.queue[index].upload();
                actionUploader.queue[index].onSuccess = function (response, status, headers) {
                    $scope.photoId = response.files[0].id;
                    $scope.count++;
                    actionUploader.queue[index].index = response.files[0].id;
                };
            };

            $scope.imgRemove = function (index) {
                if (!actionUploader.queue[index].isError) {
                    var idx = $scope.photosId.indexOf(actionUploader.queue[index].index);
                    $scope.photosId.splice(idx, 1);
                }
                $scope.count--;
                actionUploader.queue[index].remove();
            };

            /* 이미지삭제 */
            $scope.imgDeleteForm = function (id, imageId) {
                $scope.imageId = imageId;
                Action.get({
                    id: id
                }, function (result) {
                    $scope.action = result;
                    $log.debug($scope.imageId + "zz" + $scope.action.id);
                    $('#deleteImgConfirmation').modal('show');
                });
            };

            $scope.confirmImgDelete = function (id, imageId) {
                DeleteImgByAction.imgDelete({
                    id: id,
                    imageId: imageId
                }, function () {
                    $scope.reset();
                    $('#deleteImgConfirmation').modal('hide');
                    $scope.clear();
                });
            };

            $timeout(function () {
                $('#saveActionModal').on('hide.bs.modal', function () {
                    actionUploader.clearQueue();
                });
            }, 0);
        }]);
