package priv.xds.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import priv.xds.pojo.Group;

import java.util.List;

/**
 * @author HuPeng
 * @date 2021-09-13 18:23
 */
@Mapper
public interface GroupMapper {

    /**
     * 获取所有已经注册了的群
     * @return 所有已经注册了的群
     */
    List<Group> getAllRegisteredGroup();

    /**
     * 添加一个群组
     * @param groupCode 要添加的群号
     */
    void addGroup(@Param("group") String groupCode);

}
