/**
 * Date : 2012.11.30
 * Desc : 프로그램 정보를 표현할 모델 클래스
 */
Ext.define('HAMLET.model.Program', {
	extend: 'Ext.data.Model',	// extend
    fields: [
		'pgm_id',
		'pgm_key',
		'pgm_nm',
		'pgm_syscd',
		'pgm_sysnm',
		'pgm_class',
		'pgm_icon',
		'pgm_sysicon',
		'group_id',
		'group_nm',
		'group_status_cd',
		'group_status_nm',
		'group_pgm_status_nm',
		'title'
    ]
});
