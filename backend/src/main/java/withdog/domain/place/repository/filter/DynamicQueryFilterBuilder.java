package withdog.domain.place.repository.filter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DynamicQueryFilterBuilder {

    private final StringBuilder jpql = new StringBuilder();
    private final Map<String, Object> params = new HashMap<>();
    private boolean whereAdded = false;

    public DynamicQueryFilterBuilder baseQuery() {
        jpql.append("select p from Place p ");
        return this;
    }

    private void addCondition(String condition, String paramName, List<String> values) {
        if (values != null && !values.isEmpty()) {
            jpql.append(whereAdded ? " and " : " where ");
            jpql.append(condition);
            params.put(paramName, values);
            whereAdded = true;
        }
    }

    //TODO: 리펙토링 필요
    public DynamicQueryFilterBuilder withKeyword(String keyword) {
        if (keyword != null && !keyword.isBlank()) {
            jpql.append(whereAdded ? " and " : " where ")
                    .append("p.name like concat('%', :keyword, '%')");
            params.put("keyword", keyword);
            whereAdded = true;
        }

        return this;
    }

    // OR 조건 (EXISTS)
    public DynamicQueryFilterBuilder withOrFilter(String category, List<String> values) {
        if (values != null && !values.isEmpty()) {
            String condition = String.format(
                    "exists (select 1 from p.placeFilters pf " +
                            "join pf.filterOption fo " +
                            "join fo.filterCategory fc " +
                            "where pf.place = p and fc.name = '%s' and fo.value in (:%s)) ",
                    category, category
            );

            addCondition(condition, category, values);
        }

        return this;
    }

    // AND 조건 (COUNT)
    public DynamicQueryFilterBuilder withAndFilter(String category, List<String> values) {
        if (values != null && !values.isEmpty()) {
            String condition = String.format(
                    "(select count(distinct fo.value) from p.placeFilters pf " +
                            "join pf.filterOption fo " +
                            "join fo.filterCategory fc " +
                            "where pf.place = p and fc.name = '%s' and fo.value in (:%s)) = %d ",
                    category, category, values.size()
            );

            addCondition(condition, category, values);
        }

        return this;
    }

    public DynamicQueryFilterBuilder withPageNation(int offset, int limit) {
        jpql.append(" order by p.id desc"); // 정렬 내림차순
        params.put("offset", offset);
        params.put("limit", limit);
        return this;
    }

    public DynamicQueryFilterBuilder orderBy(String field) {
        jpql.append(" order by p.").append(field);
        return this;
    }

    public String getJpql() {
        return jpql.toString().trim();
    }

    public Map<String, Object> getParams() {
        return params;
    }
}
