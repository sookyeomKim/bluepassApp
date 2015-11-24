/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').config(accountConfig);

accountConfig.$inject = ['$stateProvider'];

function accountConfig($stateProvider) {
    $stateProvider.state('account', {
        abstract: true,
        parent: 'site'
    });
}
