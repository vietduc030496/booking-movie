//package com.ntu.customerservice.service;
//
//import lombok.AllArgsConstructor;
//import org.redisson.api.RLock;
//import org.redisson.api.RedissonClient;
//import org.springframework.stereotype.Service;
//
//import java.util.concurrent.TimeUnit;
//
//@Service
//@AllArgsConstructor
//public class SeatLockService {
//
//    private final RedissonClient redissonClient;
//
//    public boolean tryLockSeat(Long showtimeId, Long seatId) {
//        String lockKey = "lock:seat:" + showtimeId + ":" + seatId;
//        RLock lock = redissonClient.getLock(lockKey);
//
//        try {
//            // Thử lấy lock trong 0 giây, giữ lock trong 5 phút
//            return lock.tryLock(0, 5, TimeUnit.MINUTES);
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//            return false;
//        }
//    }
//
//    public void unlockSeat(Long showtimeId, Long seatId) {
//        String lockKey = "lock:seat:" + showtimeId + ":" + seatId;
//        RLock lock = redissonClient.getLock(lockKey);
//        if (lock.isHeldByCurrentThread()) {
//            lock.unlock(); // Chỉ thread giữ lock mới unlock được
//        }
//    }
//}
