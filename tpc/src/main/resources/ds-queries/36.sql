select address.c address.h address.o build_support.c build_support.h build_support.o calendar.dst checksum checksum.c checksum.o checksum.vcproj cities.dst column_list.txt columns.h config.h constants.h Cygwin Tools.rules date.c date.h date.o dbgen2.sln dbgen2.vcproj dbgen_version.c dbgen_version.h dbgen_version.o dcgram.c dcgram.h dcgram.o dcomp.c dcomp.h dcomp.o dcomp_params.h decimal.c decimal.h decimal.o dist.c distcomp distcomp.vcproj dist.h dist.o driver.c driver.h driver.o dsdgen dsqgen english.dst error_msg.c error_msg.h error_msg.o eval.c eval.h eval.o expr.c expr.h expr.o fips.dst genrand.c genrand.h genrand.o grammar.c grammar.h grammar.o grammar_support.c grammar_support.h grammar_support.o grammar.vcproj HISTORY How_To_Guide.doc How_To_Guide-DS-V2.0.0.docx items.dst join.c join.o keywords.c keywords.h keywords.o list.c list.h list.o load.c load.h load.o makefile Makefile Makefile.suite mathops.h misc.c misc.h misc.o mkheader mkheader.c mkheader.o mkheader.vcproj names.dst nulls.c nulls.h nulls.o parallel.c parallel.h parallel.o parallel.sh params.h permute.c permute.h permute.o porting.c porting.h PORTING.NOTES pricing.c pricing.h pricing.o print.c print.h print.o qgen2.vcproj QGEN.doc QgenMain.c QgenMain.o qgen_params.h qgen.y query_handler.c query_handler.h query_handler.o README README_grammar.txt release.c release.h ReleaseNotes.txt release.o r_params.c r_params.h r_params.o s_brand.c s_brand.h s_brand.o scaling.c scaling.dst scaling.h scaling.o s_call_center.c s_call_center.h s_call_center.o s_catalog.c s_catalog.h s_catalog.o s_catalog_order.c s_catalog_order.h s_catalog_order_lineitem.c s_catalog_order_lineitem.h s_catalog_order_lineitem.o s_catalog_order.o s_catalog_page.c s_catalog_page.h s_catalog_page.o s_catalog_promotional_item.c s_catalog_promotional_item.h s_catalog_promotional_item.o s_catalog_returns.c s_catalog_returns.h s_catalog_returns.o s_category.c s_category.h s_category.o scd.c scd.h scd.o s_class.c s_class.h s_class.o s_company.c s_company.h s_company.o s_customer_address.c s_customer_address.h s_customer_address.o s_customer.c s_customer.h s_customer.o s_division.c s_division.h s_division.o s_inventory.c s_inventory.h s_inventory.o s_item.c s_item.h s_item.o s_manager.c s_manager.h s_manager.o s_manufacturer.c s_manufacturer.h s_manufacturer.o s_market.c s_market.h s_market.o source_schema.wam sparse.c sparse.h sparse.o s_pline.c s_pline.h s_pline.o s_product.c s_product.h s_product.o s_promotion.c s_promotion.h s_promotion.o s_purchase.c s_purchase.h s_purchase.o s_reason.c s_reason.h s_reason.o s_store.c s_store.h s_store.o s_store_promotional_item.c s_store_promotional_item.h s_store_promotional_item.o s_store_returns.c s_store_returns.h s_store_returns.o s_subcategory.c s_subcategory.h s_subcategory.o s_subclass.c s_subclass.h s_subclass.o s_tdefs.h streams.h streets.dst StringBuffer.c StringBuffer.h StringBuffer.o substitution.c substitution.h substitution.o s_warehouse.c s_warehouse.h s_warehouse.o s_web_order.c s_web_order.h s_web_order_lineitem.c s_web_order_lineitem.h s_web_order_lineitem.o s_web_order.o s_web_page.c s_web_page.h s_web_page.o s_web_promotinal_item.c s_web_promotinal_item.o s_web_promotional_item.h s_web_returns.c s_web_returns.h s_web_returns.o s_web_site.c s_web_site.h s_web_site.o s_zip_to_gmt.c s_zip_to_gmt.h s_zip_to_gmt.o tables.h tags tdef_functions.c tdef_functions.h tdef_functions.o tdefs.c tdefs.h tdefs.o template.h text.c text.o tokenizer.c tokenizer.l tokenizer.o tpcds_20080910.sum tpcds.dst tpcds.idx tpcds.idx.h tpcds_ri.sql tpcds_source.sql tpcds.sql tpcds.wam validate.c validate.h validate.o w_call_center.c w_call_center.h w_call_center.o w_catalog_page.c w_catalog_page.h w_catalog_page.o w_catalog_returns.c w_catalog_returns.h w_catalog_returns.o w_catalog_sales.c w_catalog_sales.h w_catalog_sales.o w_customer_address.c w_customer_address.h w_customer_address.o w_customer.c w_customer_demographics.c w_customer_demographics.h w_customer_demographics.o w_customer.h w_customer.o w_datetbl.c w_datetbl.h w_datetbl.o w_household_demographics.c w_household_demographics.h w_household_demographics.o w_income_band.c w_income_band.h w_income_band.o w_inventory.c w_inventory.h w_inventory.o w_item.c w_item.h w_item.o w_promotion.c w_promotion.h w_promotion.o w_reason.c w_reason.h w_reason.o w_ship_mode.c w_ship_mode.h w_ship_mode.o w_store.c w_store.h w_store.o w_store_returns.c w_store_returns.h w_store_returns.o w_store_sales.c w_store_sales.h w_store_sales.o w_tdefs.h w_timetbl.c w_timetbl.h w_timetbl.o w_warehouse.c w_warehouse.h w_warehouse.o w_web_page.c w_web_page.h w_web_page.o w_web_returns.c w_web_returns.h w_web_returns.o w_web_sales.c w_web_sales.h w_web_sales.o w_web_site.c w_web_site.h w_web_site.o y.tab.c y.tab.h y.tab.o
from (select avg(ss_list_price) B1_LP
,count(ss_list_price) B1_CNT
,count(distinct ss_list_price) B1_CNTD
from store_sales
where ss_quantity between 0 and 5
and (ss_list_price between 28 and 28+10
or ss_coupon_amt between 12573 and 12573+1000
or ss_wholesale_cost between 33 and 33+20)) B1,
(select avg(ss_list_price) B2_LP
,count(ss_list_price) B2_CNT
,count(distinct ss_list_price) B2_CNTD
from store_sales
where ss_quantity between 6 and 10
and (ss_list_price between 143 and 143+10
or ss_coupon_amt between 5562 and 5562+1000
or ss_wholesale_cost between 45 and 45+20)) B2,
(select avg(ss_list_price) B3_LP
,count(ss_list_price) B3_CNT
,count(distinct ss_list_price) B3_CNTD
from store_sales
where ss_quantity between 11 and 15
and (ss_list_price between 159 and 159+10
or ss_coupon_amt between 2807 and 2807+1000
or ss_wholesale_cost between 24 and 24+20)) B3,
(select avg(ss_list_price) B4_LP
,count(ss_list_price) B4_CNT
,count(distinct ss_list_price) B4_CNTD
from store_sales
where ss_quantity between 16 and 20
and (ss_list_price between 24 and 24+10
or ss_coupon_amt between 3706 and 3706+1000
or ss_wholesale_cost between 46 and 46+20)) B4,
(select avg(ss_list_price) B5_LP
,count(ss_list_price) B5_CNT
,count(distinct ss_list_price) B5_CNTD
from store_sales
where ss_quantity between 21 and 25
and (ss_list_price between 76 and 76+10
or ss_coupon_amt between 2096 and 2096+1000
or ss_wholesale_cost between 50 and 50+20)) B5,
(select avg(ss_list_price) B6_LP
,count(ss_list_price) B6_CNT
,count(distinct ss_list_price) B6_CNTD
from store_sales
where ss_quantity between 26 and 30
and (ss_list_price between 169 and 169+10
or ss_coupon_amt between 10672 and 10672+1000
or ss_wholesale_cost between 58 and 58+20)) B6
limit 100;
