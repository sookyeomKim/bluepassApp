/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').config(['$stateProvider', function ($stateProvider) {
    $stateProvider.state('addressIndex', {
        parent: 'admin',
        url: '/addressIndexs',
        data: {
            roles: ['ROLE_USER'],
            pageTitle: 'AddressIndexs'
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/admin/addressIndex/addressIndexs.html',
                controller: 'AddressIndexController'
            }
        },
        resolve: {}
    }).state('addressIndex.detail', {
        parent: 'admin',
        url: '/addressIndex/{id}',
        data: {
            roles: ['ROLE_USER'],
            pageTitle: 'AddressIndex'
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/admin/addressIndex/addressIndex-detail.html',
                controller: 'AddressIndexDetailController'
            }
        },
        resolve: {
            entity: ['$stateParams', 'AddressIndex', function ($stateParams, AddressIndex) {
                return AddressIndex.get({
                    id: $stateParams.id
                });
            }]
        }
    }).state('addressIndex.new', {
        parent: 'addressIndex',
        url: '/new',
        data: {
            roles: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
            $modal.open({
                templateUrl: 'scripts/app/admin/addressIndex/addressIndex-dialog.html',
                controller: 'AddressIndexDialogController',
                size: 'lg',
                resolve: {
                    entity: function () {
                        return {
                            oldAddress: null,
                            id: null
                        };
                    }
                }
            }).result.then(function () {
                    $state.go('addressIndex', null, {
                        reload: true
                    });
                }, function () {
                    $state.go('addressIndex');
                })
        }]
    }).state('addressIndex.edit', {
        parent: 'addressIndex',
        url: '/{id}/edit',
        data: {
            roles: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
            $modal.open({
                templateUrl: 'scripts/app/admin/addressIndex/addressIndex-dialog.html',
                controller: 'AddressIndexDialogController',
                size: 'lg',
                resolve: {
                    entity: ['AddressIndex', function (AddressIndex) {
                        return AddressIndex.get({
                            id: $stateParams.id
                        });
                    }]
                }
            }).result.then(function () {
                    $state.go('addressIndex', null, {
                        reload: true
                    });
                }, function () {
                    $state.go('^');
                })
        }]
    });
}]);
