/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').factory('DataShare', ['$rootScope', function ($rootScope) {
    var service = {};
    service.data = false;
    service.sendData = function (data) {
        this.data = data;
        $rootScope.$broadcast('data_shared');
    };
    service.getData = function () {
        return this.data;
    };
    return service;
}]);
