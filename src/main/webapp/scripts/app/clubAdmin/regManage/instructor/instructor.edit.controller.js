/**
 * 만든이 : 수겨미
 */
'use strict';
angular.module('bluepassApp').controller('adminMypageRegManageInstructorEditController', adminMypageRegManageInstructorEditController);

adminMypageRegManageInstructorEditController.$inject = [
    '$stateParams', '$state', 'Instructor', 'FileUploader', 'Alert'
];

function adminMypageRegManageInstructorEditController($stateParams, $state, Instructor, FileUploader, Alert) {
    var vm = this;

    /* 클럽ID */
    vm.idBelongToClub = $stateParams.id;
    /*사진id초기화*/
    vm.photoId = null;
    /*강사파라미터초기화*/
    vm.instructor = {
        id: null,
        name: null,
        description: null,
        photo: {
            id: null
        },
        club: {
            id: vm.idBelongToClub
        }
    };
    /* 파일업로더 */
    vm.uploader = new FileUploader({
        url: '/image/upload',
        queueLimit: 1,
        autoUpload: true
    });
    vm.uploader.onSuccessItem = onSuccessItem;
    vm.imgUpload = imgUpload;
    vm.imgRemove = imgRemove;
    /*강사등록*/
    vm.getInstructor = getInstructor;

    if ($stateParams.insId) {
        getInstructorGet($stateParams.insId);
    }

    function getInstructorGet(id) {
        return Instructor.get({id: id}).$promise.then(function (response) {
            vm.photoId = response.photo.id;
            return vm.instructor = response
        })
    }

    function onSuccessItem(fileItem, response, status, headers) {
        vm.photoId = response.files[0].id;
    }

    function imgUpload(index) {
        vm.uploader.queue[index].upload();
        vm.uploader.queue[index].onSuccess = function (response, status, headers) {
            vm.photoId = response.files[index].id;
        };
    }

    function imgRemove(index) {
        vm.uploader.queue[index].remove();
        vm.photoId = null;
    }

    function getInstructor() {
        vm.instructor.photo.id = vm.photoId;
        if (vm.instructor.id != null) {
            return getInstructorUpdate().then(getSuccess).catch(getError)
        } else {
            return getInstructorSave().then(getSuccess).catch(getError)
        }

        function getInstructorSave() {
            return Instructor.save(vm.instructor).$promise
        }

        function getInstructorUpdate() {
            return Instructor.update(vm.instructor).$promise
        }

        function getSuccess() {
            return $state.go("adminRegManage.Instructor", {
                id: vm.idBelongToClub
            })
        }

        function getError() {
            return Alert.alert1('에러발생')
        }
    }
}
