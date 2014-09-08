package com.anderl.hibernate.ext;


import org.apache.commons.lang3.StringUtils;
import org.hibernate.sql.JoinType;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ga2unte on 12/2/13.
 */
public class CriterionFieldJoinMappings {

    public enum Alias {
        CLIENT("client", JoinType.LEFT_OUTER_JOIN),
        FRONTOFFICE("frontOffice", JoinType.LEFT_OUTER_JOIN),
        ISISINFO("isisInfo", JoinType.LEFT_OUTER_JOIN),
        CLIENT_ISISINFO("client.isisInfo", JoinType.LEFT_OUTER_JOIN),
        BRANCH("branch", JoinType.LEFT_OUTER_JOIN),
        BRANCH_ADDRESS("branch.address", JoinType.LEFT_OUTER_JOIN),
        ADDRESS("address", JoinType.LEFT_OUTER_JOIN),
        CREATOR("creator", JoinType.LEFT_OUTER_JOIN),
        CHANGEUSER("changeUser", JoinType.LEFT_OUTER_JOIN),
        ACQUIREDBY("acquiredBy", JoinType.LEFT_OUTER_JOIN),
        LOOSER("looser", JoinType.LEFT_OUTER_JOIN),
        PARENT("parent", JoinType.LEFT_OUTER_JOIN),
        ACCOUNTSETUPS("accountSetups", JoinType.LEFT_OUTER_JOIN),
        ACCOUNTSETUPS_AGENTCLIENT("accountSetups.agentClient", JoinType.LEFT_OUTER_JOIN),
        BRANCHES_ACCOUNTSETUPS("branches.accountSetups", JoinType.LEFT_OUTER_JOIN),
        PRODUCT("product", JoinType.LEFT_OUTER_JOIN),
        ACCOUNTS_AGENTCLIENT("accounts.agentClient", JoinType.LEFT_OUTER_JOIN),
        LEGALADDRESS("legalAddress", JoinType.LEFT_OUTER_JOIN),
        CLIENT_LEGALADDRESS("client.legalAddress", JoinType.LEFT_OUTER_JOIN),
        CLIENT_CREATOR("client.creator", JoinType.LEFT_OUTER_JOIN),
        BOOKINGENTITIES_PRODUCTS("bookingEntities.products", JoinType.LEFT_OUTER_JOIN),
        BOOKINGENTITIES("bookingEntities", JoinType.LEFT_OUTER_JOIN),
        BOOKINGENTITY_ACCOUNTSETUP("bookingEntity.accountSetup", JoinType.LEFT_OUTER_JOIN),
        BOOKINGENTITIES_PRODUCTS_TRADINGSYSTEMS("bookingEntities.products.tradingSystems", JoinType.LEFT_OUTER_JOIN),
        BOOKINGENTITY_ACCOUNTSETUP_CLIENT("bookingEntity.accountSetup.client", JoinType.LEFT_OUTER_JOIN),
        BOOKINGENTITY_ACCOUNTSETUP_CLIENT_ISISINFO("bookingEntity.accountSetup.client.isisInfo", JoinType.LEFT_OUTER_JOIN),
        BOOKINGENTITY_ACCOUNTSETUP_CLIENT_LEGALADDRESS("bookingEntity.accountSetup.client.legalAddress", JoinType.LEFT_OUTER_JOIN),
        BOOKINGENTITY_ACCOUNTSETUP_CLIENT_CREATOR("bookingEntity.accountSetup.client.creator", JoinType.LEFT_OUTER_JOIN),
        PRODUCT_BOOKINGENTITY_ACCOUNTSETUP("product.bookingEntity.accountSetup", JoinType.LEFT_OUTER_JOIN),
        PRODUCT_BOOKINGENTITY_ACCOUNTSETUP_CLIENT("product.bookingEntity.accountSetup.client", JoinType.LEFT_OUTER_JOIN),
        PRODUCT_BOOKINGENTITY_ACCOUNTSETUP_CLIENT_ISISINFO("product.bookingEntity.accountSetup.client.isisInfo", JoinType.LEFT_OUTER_JOIN),
        PRODUCT_BOOKINGENTITY_ACCOUNTSETUP_CLIENT_LEGALADDRESS("product.bookingEntity.accountSetup.client.legalAddress", JoinType.LEFT_OUTER_JOIN),
        PRODUCT_BOOKINGENTITY_ACCOUNTSETUP_CLIENT_CREATOR("product.bookingEntity.accountSetup.client.creator", JoinType.LEFT_OUTER_JOIN);
        private final String fieldPath;
        private final JoinType joinType;

        Alias(String fieldPath, JoinType joinType) {

            this.fieldPath = fieldPath;
            this.joinType = joinType;
        }

        public String getFieldPath() {
            return fieldPath;
        }

        public String getAliasName() {
            return fieldPath.replace(".", "_");
        }

        public JoinType getJoinType() {
            return joinType;
        }

        public List<AliasHolder> getAliases() {
            List<AliasHolder> aliases = new ArrayList<AliasHolder>();
            String aliasPath = "";
            for (String s : getFieldPath().split("\\.")) {
                if (!StringUtils.isEmpty(aliasPath)) {
                    aliasPath = aliasPath + ".";
                }
                aliasPath = aliasPath + s;
                aliases.add(new AliasHolder(aliasPath, s, this.getJoinType()));
            }
            return aliases;

        }
    }

    public static class AliasHolder {

        private final String path;
        private final String name;
        private final JoinType joinType;

        public AliasHolder(String path, String name, JoinType joinType) {
            this.path = path;
            this.name = name;
            this.joinType = joinType;
        }

        public String getPath() {
            return path;
        }

        public String getName() {
            return name;
        }

        public JoinType getJoinType() {
            return joinType;
        }
    }

    public static class CriterionMapper {
        private Alias alias;
        private String property;

        public CriterionMapper(String property, Alias alias) {
            this.property = property;
            this.alias = alias;
        }

        public CriterionMapper(String property) {
            this.property = property;
        }

        public Alias getAlias() {
            return alias;
        }

        public String getCriterionPath() {
            if (alias == null) return property;
            String fieldPath = alias.getFieldPath();
            fieldPath = fieldPath.substring(fieldPath.lastIndexOf(".") + 1);
            return fieldPath + "." + property;
        }

        public String getFieldPath() {
            return alias == null ? property : alias.getFieldPath() + "." + property;
        }


    }
}
