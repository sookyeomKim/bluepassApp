/**
 * Created by ksk on 2015-11-25.
 */
'use strict';

angular.module('bluepassApp').filter('nl2br2', nl2br2);

nl2br2.$inject = ['$sce'];

function nl2br2($sce) {
    return function (text) {
        return text ? $sce.trustAsHtml(text.replace(/\n/g, '<br/>')) : '';
    };
}
