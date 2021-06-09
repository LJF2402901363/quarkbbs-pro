package com.quark.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @Author LHR
 * Create By 2017/8/31
 */
@Service
public class RedisService {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    /**
     * 设置String缓存
     * @param key
     * @param t
     */
    public <T> void setCacheObject(String key, T t,int time){
        ValueOperations<String, Object> operations =  redisTemplate.opsForValue();
        operations.set(key,t,time, TimeUnit.HOURS);
    }
    /**
     * 获取String缓存
     * @param key
     * @return
     */
    public <T> T getCacheObject(String key){
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        T t = (T) operations.get(key);
        return t;
    }
  public <T> List<T> getCacheList(String key){
      List<Object> range = redisTemplate.opsForList().range(key, 0, -1);
      return (List<T>) range;
  }

  public <T> void setCacheList(String key, List<T> list){
        if (Objects.isNull(list) || list.size() == 0) {
            return;
        }
        list.forEach(e->{
            redisTemplate.opsForList().rightPush(key,e);
        });
  }
    /**
     * 获取String缓存并更新
     * @param key
     * @return
     */
    public <T> T getValueAndUpDate(String key,String hkey,int time){
        HashOperations<String, Object, Object> stringObjectObjectHashOperations = redisTemplate.opsForHash();
        T t = (T) stringObjectObjectHashOperations.get(key, hkey);
        if (t!=null) stringObjectObjectHashOperations.put(key,hkey,t);
        return t;
    }


    /**
     * @Description :通过获取key对应的map中的对应hkey的值
     * @Date 20:39 2021/5/29 0029
     * @Param * @param key  map的key
     * @param hkey ：map中key的值
     * @return T
     **/
  public <T>  T getValueByMapKey(String key,String hkey){
      T o = (T) redisTemplate.opsForHash().get(key, hkey);
      return  o;
  }
  public <T> Map<String, T> getMapByKey(String key){
      Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);
      Map<String,T> map = new HashMap<>();
      if (Objects.isNull(entries)||entries.size() == 0) return map;
      entries.forEach((k,v)->{
          map.put((String) k,(T) v);
      });
      return map;
  }
    /**
     * @Description :将t保存到键名为key的map中的hkey对应的值中
     * @Date 20:39 2021/5/29 0029
     * @Param * @param key  map的key
     * @param hkey ：map中key的值
     * @return T
     **/
    public <T> void setCacheMapValue(String key,String hkey,T t){
        redisTemplate.opsForHash().put(key,hkey,t);
    }
    /**
     * @Description :删除map中对应的key
     * @Date 21:22 2021/5/29 0029
     * @Param * @param key map的键名
     * @param hkey ：需要删除的map中的键名
     * @return void
     **/
    public <T> void deleteCacheMapKey(String key,String hkey){
       redisTemplate.opsForHash().delete(key, hkey);
    }
    /**
     * 删除缓存
     * @param key
     */
    public <T> void deleteObjectBykey(String key){
        redisTemplate.delete(key);
    }

    /**
     * 判断String是否存在键key
     * @param key
     * @return
     */
    public boolean mapHasKey(String key){
        ValueOperations<String, Object> operations = redisTemplate.opsForValue();
        return redisTemplate.hasKey(key);
    }

    /**
     * 设置Set缓存
     * @param key
     * @param t
     */
    public  <T> void setCacheSet(String key,T t){
        SetOperations<String,Object> opsForSet = redisTemplate.opsForSet();
        opsForSet.add(key,t);
    }
    /**
     * @Description :获取key对应的set
     * @Date 20:58 2021/5/29 0029
     * @Param * @param key ：
     * @return java.util.Set<T>
     **/
  public <T> Set<T> getCacheSetVaule(String key){
      Set<Object> members = redisTemplate.opsForSet().members(key);
      return (Set<T>) members;
  }
    /**
     * 删除Set缓存
     * @param key
     * @param t
     */
    public <T> void deleteFromSet(String key,T t){
        SetOperations<String,Object> opsForSet = redisTemplate.opsForSet();
        opsForSet.remove(key,t);
    }

    /**
     * 判断Set是否存在value
     * @param key
     * @param t
     * @return
     */
    public <T> boolean hasSetValue(String key, T t){
        SetOperations<String,Object> opsForSet = redisTemplate.opsForSet();
        return opsForSet.isMember(key, t);
    }


}
