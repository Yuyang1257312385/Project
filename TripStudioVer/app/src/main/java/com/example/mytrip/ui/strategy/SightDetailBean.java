package com.example.mytrip.ui.strategy;

/**
 * Created by yu on 2017/6/2.
 */

public class SightDetailBean {

    /**
     * error : 0
     * status : Success
     * date : 2017-06-02
     * result : {"name":"什刹海","location":{"lng":116.38098669482,"lat":39.942519453449},"telephone":"010-66127652","star":"5","url":"http://lvyou.baidu.com/shichahai","abstract":"后海湖面辽阔，风光绮丽，而且湖水比较干净。适合散步。距离南锣鼓巷很近。","description":"什刹海也写作\u201c十刹海\u201d，又名前海，四周原有十座佛寺，故有此称。元代名海子，为一宽而长的水面，明初缩小，后逐渐形成西海﹑后海﹑前海，三海水道相通。自清代起就成为游乐消夏之所，为燕京胜景之一，被誉为\u201c北方的水乡\u201d。著名的《帝京景物略》中则以\u201c西湖春，秦淮夏，洞庭秋\u201d来赞美什刹海的神韵。 景区东起地安门外大街，西到新街口北大街，北起北二环，南至平安大街，总面积146.7公顷，是京城内老北京风貌保存最完好的地方，有大量典型的胡同和四合院，这一带也是原老北京主要的商业活动区。 历史上本地区曾建有王府、寺观、庵庙等多达30余座，现仍尚存十几处。主要代表有恭王府及花园、宋庆龄故居及醇王府、 郭沫若纪念馆、钟鼓楼、德胜门箭楼、会贤堂等。 依托胡同和四合院，什刹海地区自古以来就有许多富有特色的民裕活动，如放荷灯、泛舟游湖、宴饮赏荷、冰床围酌、大阅冰鞋等。至今，一些有生命力的民俗活动仍然在什刹海地区大量存在。\u201c胡同游\u201d即活跃在这片得天独厚的自然人文环境中。而后海的酒吧一条街，更是为什刹海地区增添了现代的韵味。 古典与现代相容，传统与前卫契合，自然景观与人文胜迹辉映。闲暇之余，或品酒泛舟，览湖光粼粼，或徜徉两岸，听杨柳婆娑，或搜寻美食，尝御膳家宴，或投宿胡同人家品着原汁的京味儿。","ticket_info":{"price":"免费","open_time":"全天开放"}}
     */

    private int error;
    private String status;
    private String date;
    /**
     * name : 什刹海
     * location : {"lng":116.38098669482,"lat":39.942519453449}
     * telephone : 010-66127652
     * star : 5
     * url : http://lvyou.baidu.com/shichahai
     * abstract : 后海湖面辽阔，风光绮丽，而且湖水比较干净。适合散步。距离南锣鼓巷很近。
     * description : 什刹海也写作“十刹海”，又名前海，四周原有十座佛寺，故有此称。元代名海子，为一宽而长的水面，明初缩小，后逐渐形成西海﹑后海﹑前海，三海水道相通。自清代起就成为游乐消夏之所，为燕京胜景之一，被誉为“北方的水乡”。著名的《帝京景物略》中则以“西湖春，秦淮夏，洞庭秋”来赞美什刹海的神韵。 景区东起地安门外大街，西到新街口北大街，北起北二环，南至平安大街，总面积146.7公顷，是京城内老北京风貌保存最完好的地方，有大量典型的胡同和四合院，这一带也是原老北京主要的商业活动区。 历史上本地区曾建有王府、寺观、庵庙等多达30余座，现仍尚存十几处。主要代表有恭王府及花园、宋庆龄故居及醇王府、 郭沫若纪念馆、钟鼓楼、德胜门箭楼、会贤堂等。 依托胡同和四合院，什刹海地区自古以来就有许多富有特色的民裕活动，如放荷灯、泛舟游湖、宴饮赏荷、冰床围酌、大阅冰鞋等。至今，一些有生命力的民俗活动仍然在什刹海地区大量存在。“胡同游”即活跃在这片得天独厚的自然人文环境中。而后海的酒吧一条街，更是为什刹海地区增添了现代的韵味。 古典与现代相容，传统与前卫契合，自然景观与人文胜迹辉映。闲暇之余，或品酒泛舟，览湖光粼粼，或徜徉两岸，听杨柳婆娑，或搜寻美食，尝御膳家宴，或投宿胡同人家品着原汁的京味儿。
     * ticket_info : {"price":"免费","open_time":"全天开放"}
     */

    private ResultBean result;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private String name;
        /**
         * lng : 116.38098669482
         * lat : 39.942519453449
         */

        private LocationBean location;
        private String telephone;
        private String star;
        private String url;
        private String abstractX;
        private String description;
        /**
         * price : 免费
         * open_time : 全天开放
         */

        private TicketInfoBean ticket_info;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        public String getStar() {
            return star;
        }

        public void setStar(String star) {
            this.star = star;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getAbstractX() {
            return abstractX;
        }

        public void setAbstractX(String abstractX) {
            this.abstractX = abstractX;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public TicketInfoBean getTicket_info() {
            return ticket_info;
        }

        public void setTicket_info(TicketInfoBean ticket_info) {
            this.ticket_info = ticket_info;
        }

        public static class LocationBean {
            private double lng;
            private double lat;

            public double getLng() {
                return lng;
            }

            public void setLng(double lng) {
                this.lng = lng;
            }

            public double getLat() {
                return lat;
            }

            public void setLat(double lat) {
                this.lat = lat;
            }
        }

        public static class TicketInfoBean {
            private String price;
            private String open_time;

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getOpen_time() {
                return open_time;
            }

            public void setOpen_time(String open_time) {
                this.open_time = open_time;
            }
        }
    }
}
