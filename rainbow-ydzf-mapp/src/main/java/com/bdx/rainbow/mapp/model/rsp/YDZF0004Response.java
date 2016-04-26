package com.bdx.rainbow.mapp.model.rsp;


import java.util.List;

import com.bdx.rainbow.mapp.model.BDXBody;

/**
 * 查询执行状态
 * Created by luli on 16/2/22.
 */
public class YDZF0004Response extends BDXBody {

    /**
     * 执行状态类List
     */
    private List<ExecuteStatus> executeStatusList;

    public List<ExecuteStatus> getExecuteStatusList() {
        return executeStatusList;
    }

    public void setExecuteStatusList(List<ExecuteStatus> executeStatusList) {
        this.executeStatusList = executeStatusList;
    }


    /**
     * 执行状态类
     */
    public static class ExecuteStatus extends  BDXBody{

        /**
         * 任务类型
         */
        private String checkPlanType;
        /**
         * 已完成数量
         */
        private int completeCount;
        /**
         * 正在执行数量
         */
        private int isCompletingCount;
        /**
         * 未完成数量
         */
        private int unCompleteCount;



        public int getCompleteCount() {
            return completeCount;
        }

        public void setCompleteCount(int completeCount) {
            this.completeCount = completeCount;
        }

       

        public int getIsCompletingCount() {
            return isCompletingCount;
        }

        public void setIsCompletingCount(int isCompletingCount) {
            this.isCompletingCount = isCompletingCount;
        }

        public int getUnCompleteCount() {
            return unCompleteCount;
        }

        public void setUnCompleteCount(int unCompleteCount) {
            this.unCompleteCount = unCompleteCount;
        }

		public String getCheckPlanType() {
			return checkPlanType;
		}

		public void setCheckPlanType(String checkPlanType) {
			this.checkPlanType = checkPlanType;
		}
    }


}
