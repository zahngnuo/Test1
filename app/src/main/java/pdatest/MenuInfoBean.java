package pdatest;

import java.io.Serializable;
import java.util.List;

/**
 * desc:
 * author:张肖换
 * date:${Date}
 */

public class MenuInfoBean implements Serializable {

    private String id;           //菜单ID
    private int sysId;        //所属系统ID
    private String menuUrl;   //菜单链接
    private int isLeaf;       //是否有子节点
    private String pid;          //父级菜单ID
    private String menuName;  //菜单名称
    private String menuIcon;  //菜单图片链接
    private List<MenuInfoBean> cid;//子菜单列表(MenuInfoBean存在嵌套关系)

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getSysId() {
        return sysId;
    }

    public void setSysId(int sysId) {
        this.sysId = sysId;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    public int getIsLeaf() {
        return isLeaf;
    }

    public void setIsLeaf(int isLeaf) {
        this.isLeaf = isLeaf;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public String getMenuIcon() {
        return menuIcon;
    }

    public void setMenuIcon(String menuIcon) {
        this.menuIcon = menuIcon;
    }

    public List<MenuInfoBean> getCid() {
        return cid;
    }

    public void setCid(List<MenuInfoBean> cid) {
        this.cid = cid;
    }
}
