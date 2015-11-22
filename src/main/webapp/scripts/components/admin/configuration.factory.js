/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').factory('ConfigurationService',
    ['$rootScope', '$filter', '$http', function ($rootScope, $filter, $http) {
        return {
            get: function () {
                return $http.get('configprops').then(function (response) {
                    var properties = [];
                    angular.forEach(response.data, function (data) {
                        properties.push(data);
                    });
                    var orderBy = $filter('orderBy');
                    return orderBy(properties, 'prefix');
                });
            }
        };
    }]);
