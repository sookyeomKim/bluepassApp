/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp')
    .controller('ZipController', ['$scope', 'Zip', 'ParseLinks', function ($scope, Zip, ParseLinks) {
        $scope.zips = [];
        $scope.page = 1;
        $scope.loadAll = function () {
            Zip.query({page: $scope.page, per_page: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.zips = result;
            });
        };
        $scope.loadPage = function (page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            Zip.get({id: id}, function (result) {
                $scope.zip = result;
                jQuery('#saveZipModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.zip.id != null) {
                Zip.update($scope.zip,
                    function () {
                        $scope.refresh();
                    });
            } else {
                Zip.save($scope.zip,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            Zip.get({id: id}, function (result) {
                $scope.zip = result;
                jQuery('#deleteZipConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            Zip.delete({id: id},
                function () {
                    $scope.loadAll();
                    $('#deleteZipConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            jQuery('#saveZipModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.zip = {zipcode: null, sido: null, gugun: null, dong: null, id: null};
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };
    }]);
