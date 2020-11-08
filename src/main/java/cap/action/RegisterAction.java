package cap.action;

import cap.bean.Register;
import cap.bean.Settlement;
import cap.bean.User;
import cap.service.RegisterService;
import cap.service.SettlementService;
import cap.util.PageBean;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Map;

public class RegisterAction extends ActionSupport {
    private Register register;
    private RegisterService registerService;
    private SettlementService settlementService;
    private Integer price;
    private int pageNo=1;
    private PageBean pageBean;

    public SettlementService getSettlementService() {
        return settlementService;
    }

    public void setSettlementService(SettlementService settlementService) {
        this.settlementService = settlementService;
    }

    public String edit(){
        registerService.updateRegister(register);
        return SUCCESS;
    }
    public String add() {
        register.setprojectID(123);
        registerService.addRegister(register);

        return SUCCESS;
    }
    public String task(){
        register = registerService.findByprice(price);
        return SUCCESS;
    }

    public String list(){
        pageBean = registerService.findByPage(pageNo,10);
        return SUCCESS;
    }

    public String delete(){
        registerService.deleteRegister(price);
        return  SUCCESS;
    }
    public String update(){
        ActionContext actionContext = ActionContext.getContext();
        Map<String, Object> session = actionContext.getSession();
        User user = (User) session.get("user");
        if(user==null) {
            return "login";
        }
        if(user.getTaskIn()) {
            Settlement settlement = settlementService.findByprojectID(register.getprojectID());
            if (settlement == null) {
                settlement = new Settlement();
                settlement.setprojectID(register.getprojectID());
                settlementService.addSettlement(settlement);
                registerService.updateRegister(register);
                return SUCCESS;
            } else {
                settlementService.updateSettlement(settlement);
                registerService.updateRegister(register);
                return SUCCESS;
            }
        }else{
            return ERROR;
        }
    }

    public Register getRegister() {
        return register;
    }

    public void setRegister(Register register) {
        this.register = register;
    }

    public RegisterService getRegisterService() {
        return registerService;
    }

    public void setRegisterService(RegisterService registerService) {
        this.registerService = registerService;
    }

    public Integer getprice() {
        return price;
    }

    public void setprice(Integer price) {
        this.price = price;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public PageBean getPageBean() {
        return pageBean;
    }

    public void setPageBean(PageBean pageBean) {
        this.pageBean = pageBean;
    }
}

