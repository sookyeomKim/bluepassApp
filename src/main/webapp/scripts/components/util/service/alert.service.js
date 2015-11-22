/**
 * Created by ksk on 2015-11-14.
 */
'use strict';

angular.module('bluepassApp').service('Alert', Alert);

Alert.$inject = ['$mdDialog'];

function Alert($mdDialog) {
    this.alert1 = function (massage, title) {
        var titleText;
        title ? titleText = title : titleText = '경고';
        return $mdDialog.show($mdDialog.alert({
            clickOutsideToClose: true,
            title: titleText,
            content: massage,
            ok: '닫기'
        }))
    };

    this.alert2 = function (ev, title, massage, ok) {
        var confirm = $mdDialog.confirm({
                title: title,
                content: massage,
                ariaLabel: 'Delete confrim',
                targetEvent: ev,
                ok: ok,
                cancel: '취소'
            }
        );
        return $mdDialog.show(confirm)
    }
}
