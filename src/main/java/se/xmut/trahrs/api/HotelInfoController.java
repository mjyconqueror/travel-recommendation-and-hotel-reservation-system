package se.xmut.trahrs.api;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import se.xmut.trahrs.common.ApiResponse;
import se.xmut.trahrs.domain.model.HotelInfo;
import se.xmut.trahrs.log.annotation.WebLog;
import se.xmut.trahrs.manager.RedisService;
import se.xmut.trahrs.manager.cache.annoation.Cacheable;
import se.xmut.trahrs.service.HotelInfoService;


/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author 作者
 * @since 2022-05-20
 */
@RestController
@RequestMapping("/hotelInfo")
public class HotelInfoController {

    final Logger logger = LoggerFactory.getLogger(HotelInfoController.class);
    @Autowired
    private HotelInfoService hotelInfoService;
    @Autowired
    private RedisService redisService;

    @WebLog(description = "添加")
    @PostMapping
    public ApiResponse save(@RequestBody HotelInfo hotelInfo) {
        return ApiResponse.ok(hotelInfoService.saveOrUpdate(hotelInfo));
    }

    @WebLog(description = "用id删除")
    @DeleteMapping("/{id}")
    public ApiResponse delete(@PathVariable Integer id) {
        return ApiResponse.ok(hotelInfoService.removeById(id));
    }

    @WebLog(description = "查询全部")
    @GetMapping
    public ApiResponse findAll() {
        return ApiResponse.ok(hotelInfoService.list());
    }

    @WebLog(description = "用id查找")
    @Cacheable(cacheName = "hotelinfo", key = "#id", capacity = 3, log = true)
    @GetMapping("/{id}")
    public ApiResponse findOne(@PathVariable Integer id) {
        return ApiResponse.ok(hotelInfoService.getById(id));
    }

    @WebLog(description = "分页")
    @GetMapping("/page")
//    @Cacheable(
//
//            key = "#pageNum+'_'+#pageSize",
//            cacheName = "hotel_page",
//            capacity = 100
//    )
    public ApiResponse findPage(@RequestParam Integer pageNum,
                                @RequestParam Integer pageSize) {
        return ApiResponse.ok(hotelInfoService.page(new Page<>(pageNum, pageSize)));
    }

}

