/**
 * 만든이 : 수겨미
 */
'use strict';
angular.module('bluepassApp').controller(
    'adminMypageRegManageInstructorEditController',
    ['$scope', '$log', '$timeout', '$stateParams', '$state', 'Instructor', 'InstructorByClub', 'FileUploader',
        function ($scope, $log, $timeout, $stateParams, $state, Instructor, InstructorByClub, FileUploader) {
            /* 클럽ID */
            var idBelongToClub = $scope.idBelongToClub = $stateParams.id;

            /* 초기화 */
            $scope.clear = function () {
                $scope.photoId = null;
                $scope.instructor = {
                    id: null,
                    name: null,
                    description: null
                };
            };
            $scope.clear();

            $scope.instructorInfoUpdate = function (id) {
                Instructor.get({
                    id: id
                }, function (result) {
                    $scope.instructor = result;
                    $scope.photoId = $scope.instructor.photo.id;
                });
            };

            if ($stateParams.insId) {
                $scope.instructorInfoUpdate($stateParams.insId);
            }

            $scope.save = function () {
                if ($scope.instructor.id != null) {
                    var instructorUpdate = Instructor.update($scope.instructor);
                    instructorUpdate.$promise.then(function (success) {
                        if (success) {
                            $state.go("adminRegManage.Instructor", {
                                id: idBelongToClub
                            })
                        } else {
                            $scope.alert = "에러발생";
                            jQuery("#confirmationModal").modal("show");
                        }
                    });
                } else {
                    var instructorSave = Instructor.save($scope.instructor);
                    instructorSave.$promise.then(function (success) {
                        if (success) {
                            $state.go("adminRegManage.Instructor", {
                                id: idBelongToClub
                            })
                        } else {
                            $scope.alert = "에러발생";
                            jQuery("#confirmationModal").modal("show");
                        }
                    });
                }
            };

            /* 파일업로더 */
            var uploader = $scope.uploader = new FileUploader({
                url: '/image/upload',
                queueLimit: 1,
                autoUpload: true
            });

            uploader.onSuccessItem = function (fileItem, response, status, headers) {
                $scope.photoId = response.files[0].id;
            };

            $scope.imgUpload = function (index) {
                uploader.queue[index].upload();
                uploader.queue[index].onSuccess = function (response, status, headers) {
                    $scope.photoId = response.files[index].id;
                };
            };

            $scope.imgRemove = function (index) {
                uploader.queue[index].remove();
                $scope.photoId = null;
            };
        }]);
