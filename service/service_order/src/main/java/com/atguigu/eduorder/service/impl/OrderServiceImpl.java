package com.atguigu.eduorder.service.impl;

import com.atguigu.commonutils.ordervo.CourseWebVoOrder;
import com.atguigu.commonutils.ordervo.UcenterMemberOrder;
import com.atguigu.eduorder.client.CourseOrderClient;
import com.atguigu.eduorder.client.UcenterOrderClient;
import com.atguigu.eduorder.entity.Order;
import com.atguigu.eduorder.mapper.OrderMapper;
import com.atguigu.eduorder.service.OrderService;
import com.atguigu.eduorder.utils.OrderNoUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-10-06
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private CourseOrderClient courseOrderClient;
    @Autowired
    private UcenterOrderClient ucenterOrderClient;

    //生成订单的方法
    @Override
    public String createOrders(String courseId, String memberId) {
       //通过远程调用获取用户信息（通过用户id）
        UcenterMemberOrder userInfoOrder = ucenterOrderClient.getInfoUc(memberId);
        //通过远程调用获取课程信息（通过课程id）
        CourseWebVoOrder courseInfoOrder = courseOrderClient.getCourseInfoOrder(courseId);
        //创建Order对象，向order对象里面设置需要的参数
        Order order = new Order();
        //创建订单
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberId);
        order.setMail(userInfoOrder.getMail());
        order.setNickname(userInfoOrder.getNickname());
        order.setStatus(0);//支付状态，支付为1 未支付为0
        order.setPayType(1);//支付类型 默认为1
        baseMapper.insert(order);
        //返回订单号
        return order.getOrderNo();


    }
}
