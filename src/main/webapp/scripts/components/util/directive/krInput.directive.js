/**
 * Created by ksk on 2015-11-15.
 */
'use strict';

angular.module('bluepassApp').directive('krInput', krInput);

krInput.$inject = ['$parse'];

function krInput($parse) {
    var directive = {
        priority: 2,
        restrict: 'A',
        compile: compile
    };
    return directive;

    function compile(el) {
        el.on('compositionstart', function (e) {
            e.stopImmediatePropagation();
        });
    }
}
