/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').factory('MonitoringService', ['$rootScope', '$http', function ($rootScope, $http) {
    return {
        getMetrics: function () {
            return $http.get('metrics/metrics').then(function (response) {
                return response.data;
            });
        },

        checkHealth: function () {
            return $http.get('health').then(function (response) {
                return response.data;
            });
        },

        threadDump: function () {
            return $http.get('dump').then(function (response) {
                return response.data;
            });
        }
    };
}]);
