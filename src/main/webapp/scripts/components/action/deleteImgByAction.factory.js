/**
 * Created by ksk on 2015-11-25.
 */
'use strict';

angular.module('bluepassApp').factory('DeleteImgByAction', DeleteImgByAction);

DeleteImgByAction.$inject = ['$resource'];

function DeleteImgByAction($resource) {
    return $resource('/api/actions/:id/image/:imageId', {
        id: '@id'
    }, {
        'imgDelete': {
            method: 'delete',
            params: {
                imageId: '@imageId'
            }
        }
    });
}
