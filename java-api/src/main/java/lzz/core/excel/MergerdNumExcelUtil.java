package lzz.core.excel;

import cn.hutool.core.text.csv.CsvData;
import cn.hutool.core.text.csv.CsvReader;
import cn.hutool.core.text.csv.CsvRow;
import cn.hutool.core.text.csv.CsvUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MergerdNumExcelUtil {
    public static List<String> getOriginExcelData() {
        String filePath = "";
        URL uri = MergerdNumExcelUtil.class.getResource("/");

        filePath = uri.getPath();
        filePath += "Yearning_Data (9).csv";
        Path paths = Paths.get(filePath.substring(1));
        CsvReader reader = CsvUtil.getReader();
        CsvData csvRows = reader.read(paths, StandardCharsets.UTF_8);
        List<CsvRow> lists = csvRows.getRows();
        List<String> fullList = new ArrayList<>();
        lists.forEach(row-> {
            fullList.addAll(row.getRawList());
        });
        // 497976
        return fullList;
    }

    public static List<XiechengData> getSubExcelDataList() {
        String filePath = "";
        URL uri = MergerdNumExcelUtil.class.getResource("/");
        filePath = uri.getPath();
        filePath += "sub.xlsx";
        Path paths = Paths.get(filePath.substring(1));
        ExcelReader reader = ExcelUtil.getReader(paths.toFile());
        return reader.read(0,1, XiechengData.class);
    }
    public static void main(String[] args) {

        URL uri = MergerdNumExcelUtil.class.getResource("/");

        String filePath = uri.getPath();
        File file = new File(filePath + "/result.txt");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {
            file.delete();
        }

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        List<String> originDataList = getOriginExcelData();
        System.out.println("源数据总数：" + originDataList.size());

        List<XiechengData> xiechengDataList = getSubExcelDataList();
        System.out.println("excel数据总数：" + xiechengDataList.size());
        int total = 0;
        for (String originData : originDataList) {
            for (XiechengData xiechengData : xiechengDataList) {
                if (xiechengData.getChildId().equals(originData) || xiechengData.getParentId().equals(originData)) {
                    try {
                        total++;
                        fos.write((originData +  "\r\n").getBytes(StandardCharsets.UTF_8) );
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        System.out.println("合并总数为：" + total);
    }

    public static class XiechengData {
        private String parentId;
        private String childId;
        private String hotelName;

        public String getParentId() {
            return parentId;
        }

        public void setParentId(String parentId) {
            this.parentId = parentId;
        }

        public String getChildId() {
            return childId;
        }

        public void setChildId(String childId) {
            this.childId = childId;
        }

        public String getHotelName() {
            return hotelName;
        }

        public void setHotelName(String hotelName) {
            this.hotelName = hotelName;
        }
    }
}
