package hungry.bean;

import java.util.List;

/**
 * desc:
 * author:张肖换
 * date:${Date}
 */
public class DishMenu {
    private String menuName;
    private List<Dish> dishList;

    public DishMenu() {
    }

    public DishMenu(String menuName, List<Dish> list) {
        this.menuName = menuName;
        this.dishList = list;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public List<Dish> getDishList() {
        return dishList;
    }

    public void setDishList(List<Dish> dishList) {
        this.dishList = dishList;
    }
}
