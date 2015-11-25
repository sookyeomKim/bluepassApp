/**
 * Created by ksk on 2015-11-25.
 */
'use strict';

angular.module('bluepassApp').factory('CodeNameCommonCode', CodeNameCommonCode);

CodeNameCommonCode.$inject = ['$resource'];

function CodeNameCommonCode($resource) {
    return $resource('api/commonCodes/:codeName/children', {
        codeName: '@codeName'
    }, {
        'query': {
            method: 'GET',
            isArray: true
        }
    });
}
