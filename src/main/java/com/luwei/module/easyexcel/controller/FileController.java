package com.luwei.module.easyexcel.controller;

import com.alibaba.excel.write.handler.WriteHandler;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.fastjson.JSON;
import com.luwei.module.easyexcel.envm.OrderStatusEnum;
import com.luwei.module.easyexcel.listener.impl.UserListener;
import com.luwei.module.easyexcel.pojo.ErrRows;
import com.luwei.module.easyexcel.pojo.Order;
import com.luwei.module.easyexcel.pojo.User;
import com.luwei.module.easyexcel.service.UserService;
import com.luwei.module.easyexcel.utils.EasyExcelParams;
import com.luwei.module.easyexcel.utils.EasyExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: WuKun
 * @Date: 2019/4/17 14:34
 * <p>
 * 演示demo
 */
@Slf4j
@RestController
public class FileController {
    /*---------------------------------------------- Fields ~ ----------------------------------------------*/
    //用于测试EasyExcelUtil
    private List<Order> data = new ArrayList<>();

    /*---------------------------------------------- Methods ~ ----------------------------------------------*/

    /**
     * 使用EasyExcelUtil导出Excel 2003
     *
     * @param response
     * @throws Exception
     */
    @GetMapping("/easy2003")
    public void easy2003(HttpServletResponse response) throws Exception {
        initData();

        //设置参数
        EasyExcelParams params = new EasyExcelParams().setResponse(response)
                .setExcelNameWithoutExt("Order(xlsx)")
                .setSheetName("第一张sheet")
                .setData(data)
                .setDataModelClazz(Order.class);

        long begin = System.currentTimeMillis();
        EasyExcelUtil.exportExcel2003(params);
        long end = System.currentTimeMillis();

        log.info("-----EasyExcelUtils : 导出成功,导出excel花费时间为 : " + ((end - begin) / 1000) + "秒");
    }

    /**
     * 使用EasyExcelUtils 导出Excel 2007
     *
     * @param response HttpServletResponse
     * @throws Exception exception
     */
    @GetMapping("/easy2007")
    public void easy2007(HttpServletResponse response) throws Exception {
        initData();

        //设置参数
        EasyExcelParams params = new EasyExcelParams().setResponse(response)
                .setExcelNameWithoutExt("Order(xlsx)")
                .setSheetName("第一张sheet")
                .setData(data)
                .setDataModelClazz(Order.class)
                .checkValid();

        long begin = System.currentTimeMillis();
        EasyExcelUtil.exportExcel2007(params);
        long end = System.currentTimeMillis();

        log.info("-----EasyExcelUtils : 导出成功,导出excel花费时间为 : " + ((end - begin) / 1000) + "秒");
    }

    private void initData() {
        if (CollectionUtils.isEmpty(data)) {
            for (int i = 0; i < 60000; i++) {
                Order order = new Order();
                order.setPrice(BigDecimal.valueOf(11.11));
                order.setCreateTime(LocalDateTime.now());
                order.setGoodsName("香蕉");
                order.setOrderId(i);
                order.setNum(11);
                order.setOrderStatus(OrderStatusEnum.PAYED);
                data.add(order);
            }
        }
    }


    /**
     * 导出User相关模板
     */
    @GetMapping("/easy2007/template/user")
    public void easy2007Template4User(HttpServletResponse response) throws IOException {
        //设置参数
        EasyExcelParams params = new EasyExcelParams().setResponse(response)
                .setExcelNameWithoutExt("Order(xlsx)")
                .setSheetName("第一张sheet")
                .setData(new ArrayList<User>())
                .setDataModelClazz(User.class)
                .checkValid();

        long begin = System.currentTimeMillis();
        EasyExcelUtil.exportExcel2007(params);
        long end = System.currentTimeMillis();

        log.info("-----EasyExcelUtils : 导出成功,导出excel花费时间为 : " + ((end - begin) / 1000) + "秒");
    }

    /**
     * 使用EasyExcelUtils 导出Excel 2007
     *
     * @param response HttpServletResponse
     * @throws Exception exception
     */
    @GetMapping("/styleExport2003")
    public void styleExport2003(HttpServletResponse response) throws Exception {
        initData();
        //设置参数
        EasyExcelParams params = new EasyExcelParams().setResponse(response)
                .setExcelNameWithoutExt("Order(xls)")
                .setSheetName("第一张sheet")
                .setData(data)
                .setWriteHandlers(getWriteHandler())
                .setDataModelClazz(Order.class)
                .checkValid();

        long begin = System.currentTimeMillis();
        EasyExcelUtil.exportExcel2003(params);
        long end = System.currentTimeMillis();

        log.info("-----EasyExcelUtils : 导出成功,导出excel花费时间为 : " + ((end - begin) / 1000) + "秒");
    }

    /**
     * 使用EasyExcelUtils 导出Excel 2007
     *
     * @param response HttpServletResponse
     * @throws Exception exception
     */
    @GetMapping("/styleExport2007")
    public void styleExport2007(HttpServletResponse response) throws Exception {
        initData();
        //设置参数
        EasyExcelParams params = new EasyExcelParams().setResponse(response)
                .setExcelNameWithoutExt("Order(xlsx)")
                .setSheetName("第一张sheet")
                .setData(data)
                .setWriteHandlers(getWriteHandler())
                .setDataModelClazz(Order.class)
                .checkValid();

        long begin = System.currentTimeMillis();
        EasyExcelUtil.exportExcel2007(params);
        long end = System.currentTimeMillis();

        log.info("-----EasyExcelUtils : 导出成功,导出excel花费时间为 : " + ((end - begin) / 1000) + "秒");
    }

    /**
     * 获取样式集合
     *
     * @return
     */
    private List<WriteHandler> getWriteHandler() {
        // 表头样式
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontHeightInPoints((short)10);
        headWriteFont.setColor(IndexedColors.RED.getIndex());
        headWriteCellStyle.setWriteFont(headWriteFont);
        // headWriteCellStyle.setFillForegroundColor(IndexedColors.BLACK.getIndex());
        // 内容样式
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        HorizontalCellStyleStrategy horizontalCellStyleStrategy =
                new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
        List<WriteHandler> writeHandlers = new ArrayList<>();
        writeHandlers.add(horizontalCellStyleStrategy);
        // OnceAbsoluteMergeStrategy onceAbsoluteMergeStrategy = new OnceAbsoluteMergeStrategy(0,0, 5,6);
        // LoopMergeStrategy loopMergeStrategy = new LoopMergeStrategy(2, 0);
        // writeHandlers.add(loopMergeStrategy);
        return writeHandlers;
    }

    @Autowired
    private UserService userService;

    /**
     * 读取测试
     *
     * @param excel excel文件
     */
    @PostMapping("/readExcel")
    public void readExcel(@RequestParam MultipartFile excel) {
        List<ErrRows> errRows = EasyExcelUtil.readExcel(excel, User.class, new UserListener(userService));
        log.info("/*------- 错误的行号数为 :  {}-------*/", JSON.toJSONString(errRows));
    }

}
