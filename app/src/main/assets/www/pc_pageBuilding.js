/**
 * Created by 田富杰 on 2018/05/22.
 */
var app = angular.module('pcPage', []);
app.controller("page", function($scope) {


    angular.element(document).ready(function () {
        mapInit();
        var height= $(document).height();
        document.getElementById("map").style.height= height+"px";
         document.getElementById("title").style.height= height*0.06+"px";
    });

    //地图初始化


    var getPoint = function(){
        map.clearOverlays();
        var pp = local.getResults().getPoi(0).point;    //获取第一个智能搜索的结果
        map.centerAndZoom(pp, zoomSize);
        map.addOverlay(new BMap.Marker(pp));

    };

    //地图初始化
    var map,myGeo,local,zoomSize, resultPanel;
    var countryT;

    var mapInit = function(){
        map = new BMap.Map("map_canvas");
        var point = new BMap.Point(116.331398,39.897445);
        map.centerAndZoom(point,7);

        //启用滚轮放大缩小
        map.enableScrollWheelZoom(true);

        //启用双击放大
        map.enableDoubleClickZoom(true);

        //启用键盘操作
        map.enableKeyboard(true);

        map.addControl(new BMap.NavigationControl());
        myGeo = new BMap.Geocoder();
        resultPanel = new BMap.Autocomplete(    //建立一个自动完成的对象
            {"input":"toGetResult",
                "location" : map
            });

        local = new BMap.LocalSearch(map, { //智能搜索
            onSearchComplete: getPoint,
            renderOptions:{map: map}
        });
        // 保存 touch 对象信息
        var obj = {};
        map.addEventListener('touchstart', function (e) {
            obj.e = e.changedTouches ? e.changedTouches[0] : e;
            obj.target = e.target;
            obj.time = Date.now();
            obj.X = obj.e.pageX;
            obj.Y = obj.e.pageY;
        });

        map.addEventListener("touchend", function(e){
            if (obj.target === e.target &&((Date.now() - obj.time) < 750) &&(Math.sqrt(Math.pow(obj.X - obj.e.pageX, 2) + Math.pow(obj.Y - obj.e.pageY, 2)) < 15)) {

                map.clearOverlays();
                var pt = e.point;
                var addr = "";
                map.addOverlay(new BMap.Marker(pt));
                myGeo.getLocation(pt, function (rs) {

                    var addrArray = rs.address.split(", ");
                    if (addrArray.length > 1) {
                        countryT = addrArray[addrArray.length - 1];
                    }
                    else {
                        countryT = "中国";
                    }

                    var addComp = rs.addressComponents;
                    if (addComp.province != "") {

                    }
                    addr = addComp.province + "/" + addComp.city + "/" + addComp.district + "/-" + countryT;
                    $scope.provinceCityDistrict = addr;
                    $scope.lnglatText = pt.lng + "," + pt.lat + "(" + addr + ")";
                    var d = pt.lng + "," + pt.lat + "(" + addr + ")";
                    $("#lnglatText").val(d);
                    $scope.latitudeLongitude = pt.lng + "," + pt.lat;
                    ($scope.$$phase || $scope.$root.$$phase) ? "" : $scope.$digest();
                });
            }
        });

        resultPanel.addEventListener("onhighlight", function(e) {  //鼠标放在下拉列表上的事件
            var str = "";
            var _value = e.fromitem.value;
            var value = "";
            if (e.fromitem.index > -1) {
                value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
            }
            str = "FromItem<br />index = " + e.fromitem.index + "<br/>value = " + value;

            value = "";
            if (e.toitem.index > -1) {
                _value = e.toitem.value;
                value = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
            }
            str += "<br />ToItem<br />index = " + e.toitem.index + "<br/>value = " + value;
            document.getElementById("searchResultPanel").innerHTML = str;
        });


        resultPanel.addEventListener("onconfirm", function(e) {    //鼠标点击下拉列表后的事件

            var myValue;
            var _value = e.item.value;
            myValue = _value.province +  _value.city +  _value.district +  _value.street +  _value.business;
            document.getElementById("searchResultPanel").innerHTML ="onconfirm<br/>index = " + e.item.index + "<br />myValue = " + myValue;

            zoomSize = 15;
            local.search(myValue);
        });
    };

    //地图模态框搜索
    $scope.mapModalSearch = function(){
        var searchText=$scope.mapModalSearchText;

        local.search(searchText);
        zoomSize = 15;
    };

    //地图模态框重置
    $scope.mapModalReset = function(){
        $scope.provinceCityDistrict = "";
         $scope.latitudeLongitude = "";
        $scope.mapModalSearchText = "";
        map.clearOverlays();
    };

    //地图模态框确认
    $scope.mapModalSure = function(){
      var name = document.getElementById("provinceCityDistrict").value+"@"+document.getElementById("latitudeLongitude").value;
        window.AndroidWebView.showInfoFromJs(name);

    };
      //地图取消
        $scope.cancel = function(){
            window.AndroidWebView.cancelJs();

        };

//    var tipToWord = {"AU": "澳大利亚",
//        "GB": "英国",
//        "US": "美国",
//        "CN": "中国",
//        "KR":"韩国",
//        "IN":"印度",
//        "MY":"马来西亚",
//        "TR":"土耳其",
//        "RUS":"俄罗斯"
//    };
//         $('#options').flagStrap({
//             countries: {
//                 "AU": "澳大利亚",
//                 "GB": "英国",
//                 "US": "美国",
//                 "CN": "中国",
//                 "KR":"韩国",
//                 "IN":"印度",
//                 "MY":"马来西亚",
//                 "TR":"土耳其",
//                 "RUS":"俄罗斯"
//             },
//             placeholder: {
//                 value: "",
//                 text: "请选择国家"
//             },
//             buttonSize: "btn-mi",
//             buttonType: "btn-info",
//             labelMargin: "10px",
//             scrollable: false,
//             scrollableHeight: "300px",
//             onSelect: function(value, element) {
//                 zoomSize = 8;
//                 console.log(tipToWord[value]);
//
//                 local.search(tipToWord[value]);
//             }
//         });



    // });
});