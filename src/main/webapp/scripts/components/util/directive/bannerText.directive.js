/**
 * 만든이 : 수겨미
 */
"use strict";

angular.module("bluepassApp").directive("bannerText", [function () {
    function responseProp() {
        /* 배너CSS속성 */
        var currentWidth = window.innerWidth;
        var currentHeight = 0;
        var currentFontSize = 0;
        var currentFontWeight = 0;

        var responseWidth = window.innerWidth;

        if (responseWidth < 600) {
            currentWidth = currentWidth * 0.43;
            currentHeight = "22px";
            currentFontSize = "12px";
            currentFontWeight = "400";
        } else if (currentWidth >= 600 && currentWidth <= 959) {
            currentWidth = currentWidth / 2 * 0.43;
            currentHeight = "25px";
            currentFontSize = "14px";
            currentFontWeight = "500";
        } else if (currentWidth >= 960 && currentWidth <= 1199) {
            currentWidth = currentWidth / 3 * 0.43;
            currentHeight = "30px";
            currentFontSize = "16px";
            currentFontWeight = "500";
        } else if (currentWidth >= 1200) {
            currentWidth = currentWidth / 3 * 0.43;
            currentHeight = "30px";
            currentFontSize = "16px";
            currentFontWeight = "500";
        }

        return {
            currentWidth: currentWidth,
            currentHeight: currentHeight,
            currentFontSize: currentFontSize,
            currentFontWeight: currentFontWeight
        }
    }

    return {
        restrict: "E",
        template: "<div>{{inText}}</div>",
        scope: {
            inText: "@outText",
            inName: "@outName"
        },
        link: function (sco, ele) {
            var backgourndColor;
            if (sco.inName) {
                if (sco.inName === "SPORTART_001")
                    backgourndColor = "#Eacb37";
                else if (sco.inName === "SPORTART_002")
                    backgourndColor = "#de4343";
                else if (sco.inName === "SPORTART_003")
                    backgourndColor = "#50bda7";
                else if (sco.inName === "SPORTART_004")
                    backgourndColor = "#Fd9424";
                else if (sco.inName === "SPORTART_005")
                    backgourndColor = "#1d9c6b";
                else if (sco.inName === "SPORTART_006")
                    backgourndColor = "#9d69cd";
                else if (sco.inName === "SPORTART_007")
                    backgourndColor = "#f25392";
                else if (sco.inName === "SPORTART_008")
                    backgourndColor = "#F18369";
                else if (sco.inName === "SPORTART_009")
                    backgourndColor = "#b56fc1";
                else if (sco.inName === "SPORTART_010")
                    backgourndColor = "#5658d0";
                else if (sco.inName === "SPORTART_011")
                    backgourndColor = "#78b1e0";
                else if (sco.inName === "SPORTART_012")
                    backgourndColor = "#5cc656";
                else if (sco.inName === "SPORTART_013")
                    backgourndColor = "#3662D2";
                else if (sco.inName === "SPORTART_014")
                    backgourndColor = "#A2E857"
            } else {
                backgourndColor = "#0f0f0f"
            }
            ele.css({
                "line-height": "1.8",
                "color": "#fff",
                "opacity": "0.8",
                "background-color": backgourndColor
            });

            ele.css({
                "width": responseProp().currentWidth,
                "height": responseProp().currentHeight,
                "font-size": responseProp().currentFontSize,
                "font-weight": responseProp().currentFontWeight
            });
            $(window).resize(function () {
                ele.css({
                    "width": responseProp().currentWidth,
                    "height": responseProp().currentHeight,
                    "font-size": responseProp().currentFontSize,
                    "font-weight": responseProp().currentFontWeight
                });
            });
        },
        replace: true
    }
}]);
