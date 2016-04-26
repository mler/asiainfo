    var zoomNum = 4;	//地图放大级别
    var popomap;		//mapManager实例化对象
    var ocnmap;			//都市圈地图实例化对象
    var tdtmap;         //天地图实例化对象
    var trans = null;   //坐标转换实例化对象

    function pageLoad() {
        trans = new Transformation(45, 45, 49);   //初始化坐标转换实例
        initOcnMap();  //初始化都市圈地图
        //initTDTMap();	//初始化天地图

        popomap = new MapManager();  //实例化新的Mapmanager对象
        popomap.init(ocnmap);

        //ajaxMap();
    }

    // 重新在地图上进行标注
    function ajaxMap() {
        var data = {'orgids' : $('#orgids').val(), 'companyName': $('#companyName').val(), 'companyTypes' : $('#companyTypes').val(), 'companyId': $('#companyId').val()};
        $.ajax({
            type: "POST",
            url: "/CompanyList/listCompanyMap",
            data: data,
            async: false,
            beforeSend: function () {
            },
            error: function (aa) {
            },
            success: function (data) {
                try{
                    popomap.allClean();
                    if(data[0].address) {
                        drawPopOpen(data);
                    } else {
                        // 返回登录页面
                        parent.location.href = '/';
                    }
                } catch(e) {

                }
            }
        });
    }

    // 设置地图放大级别
    function zoomTo(zoom) {
        popomap.zoomTo(zoom);
    }

    /*
    重新画标注点
     */
    function reDrawPoint(_els) {
        popomap.allClean();

        if(_els) {
            var point = [];
            _els.each(function() {
                if(!$(this).hasClass('fenye')) {
                    point.push({
                        longitude: $(this).attr('longitude'),
                        latitude: $(this).attr('latitude'),
                        companyId: $(this).attr('companyId'),
                        companyName: $(this).attr('companyName'),
                        index: $(this).attr('index')
                    })
                }
            });

            if(point.length > 0) {
                drawPopOpen(point);
            }
        }
    }

    /*
     测试绘制可点击标注方法，标注样式通过自定义html决定，
     标注点击打开窗口通过在html中设置点击触发parent.popomap.createIframePop方法即可，打开内容通过url传入
     */
    function drawPopOpen(data) {
        for (var i = 0; i < data.length; i++) {
            var comp = data[i];
            // 把经度纬度转变成X/Y轴坐标
            var ocn = trans.WGS842OCN(comp.longitude, comp.latitude);
            var x = ocn.x;
            var y = ocn.y;
            var html = '<div class="ibox" companyId="'+comp.companyId +'" name="'+ comp.companyName +'" x="'+ x +'" y="' + y + '" style=""><div _child="body" class="ifont">' +
                '<div class="ipoint">' +
                '<span>' + comp.index + '</span><img _child="point" title="'+ comp.companyName +'" src="/resources/images/dhmap/marker2.png"/></div></div></div>';
            popomap.createPop('MARK'+comp.companyId, html, x, y, -14, -30, 0);

            if(i == 0) {
                popomap.zoomAndCenter(x, y, zoomNum);
            }
        }
        $('div.ibox').click(function() {
            $('.olwidgetPopupCloseBox').click();
            popomap.createIframePop('POP'+$(this).attr('id'), $(this).attr('name') + '&nbsp;&nbsp;<span style="font-size: 14px"><a style="color: #428bca" href="javascript:companyDetailInfo(' + $(this).attr('companyId') + ')">详情&gt;&gt;</a></span>', '/CompanyList/companyInfo?companyId='+$(this).attr('companyId'), 300, 120, $(this).attr('x'), $(this).attr('y'));
        });
    }

    function showCustomDetial(pt, entity){

    }

    /*
     都市圈地图初始化
     */
    function initOcnMap() {
        var config = new OMAP.Config({
            imagePath : '/image',
            jsPath : url + '/resource/js',
            mapId : 1,
            scale : 0.353,
            hotFileLevel : 5,
            overlook : Math.PI / 4,
            rotate : Math.PI / 4
        });
        var ext = new OMAP.Bounds(-56255400.354765005,-56255400.354765005,56255400.354765005,56255400.354765005);
        var layer25D = new OMAP.Layer.NOGISLayer("25D", url + "/resource/",{
            isBaseLayer:false,
            transparent:true,
            defaultImage : url + '/api/img/transparent.png',
            loadHotspot: true
        });
        var layerYX = new OMAP.Layer.NOGISLayer("YX", url + "/resource/yx",{
            isBaseLayer:true,
            defaultImage : url + '/api/img/nopic.jpg',
            loadHotspot: false
        });
        var mapOptions = {
            extent : ext,
            center:[7665927.22, 4657734.33],
            zoom: zoomNum,
            config: config,
            resolutions : [107.29866095498084608,53.64933047749042304,26.82466523874521152,13.41233261937260576,
                6.70616630968630288,3.35308315484315144,1.67654157742157572,0.83827078871078786,0.41913539435539393],
            numZoomLevels: 9,
            layers:[layer25D,layerYX],
            controls : [ new OMAP.Control.PanZoomBar(),
                new OMAP.Control.MousePosition(),
                new OMAP.Control.Navigation() ]
        };
        ocnmap = new OMAP.Map("mapDiv", mapOptions);
    }

    /*
     天地图初始化
     */
    function initTDTMap() {
        var url = "http://210.74.129.78:8399/arcgis/rest/services/tdtNewMap/MapServer";
        var tdtLayer = new OMAP.Layer.XYTDTLayer("TDTMap",url);
        var mapOptions = {
            resolutions:[
                0.08789062500000003,
                0.043945312500000014,
                0.021972656250000007,
                0.010986328125000003,
                0.005493164062500002,
                0.002746582031250001,
                0.0013732910156250004,
                6.866455078125002E-4,
                3.433227539062501E-4,
                1.7166137695312505E-4,
                8.583068847656253E-5,
                4.2915344238281264E-5,
                2.1457672119140632E-5,
                1.0728836059570316E-5,
                5.364418029785158E-6
            ],
            layers:[tdtLayer],
            numZoomLevels:15,
            zoom: 10,
            controls : [ new OMAP.Control.PanZoomBar(),
                new OMAP.Control.MousePosition(),
                new OMAP.Control.Navigation() ],
            center:[108.7239051043, 34.328985370913]
        };
        tdtmap = new OMAP.Map("map1",mapOptions);
    }

    // 地图切换
    var mapType = 1; // 1：2.5D，2：矢量，3：影像
    var mapTypeManager = {
        switchMapType : function(ele) {
            if (mapType == 1) {
                this.setMapType(2);
            } else if(mapType == 2) {
                this.setMapType(1);
            }
        },
        setMapType : function(type) {
            var ele = $('#sl');
            mapType = type;
            if(type == 1){
                $('#mapDiv').style.display = 'block';
                $('#map1').style.display = 'none';

                ele.src = 'http://61.185.20.73:58888/img/mapType/sl.png';
                ele.title = '切换到矢量';

                //var ctr = tdtmap.getCenter();
                //var ocn = trans.WGS842OCN(ctr.lon,ctr.lat);
                //ocnmap.setCenter([ocn.x,ocn.y], zoomNum);
            }
            else if (type == 2) {
                $('#mapDiv').style.display = 'none';
                $('#map1').style.display = 'block';

                ele.src = 'http://61.185.20.73:58888/img/mapType/25d.png';
                ele.title = '切换到2.5D';

                //var ctr = ocnmap.getCenter();
                //tdtmap.setCenter([ctr.lon, ctr.lat], zoomNum);
            }
        }
    };
