package redempt.redlib.configmanager;

import org.bukkit.configuration.ConfigurationSection;
import redempt.redlib.configmanager.annotations.ConfigMappable;
import redempt.redlib.configmanager.exceptions.ConfigMapException;

import java.util.ArrayList;

class ConfigList<T> extends ArrayList<T> implements ConfigStorage {
	
	protected Class<T> clazz;
	private ConfigObjectMapper<T> mapper;
	private ConfigurationSection section;
	private ConfigManager manager;
	private ConversionType type;
	
	public ConfigList(Class<T> clazz, ConversionType type) {
		this.clazz = clazz;
		this.type = type;
	}
	
	@Override
	public void init(ConfigManager manager) {
		if (this.manager != null) {
			return;
		}
		mapper = new ConfigObjectMapper<>(clazz, type, manager);
		this.manager = manager;
		
	}
	
	@Override
	public void save(ConfigurationSection section) {
		int[] count = {0};
		forEach(i -> {
			mapper.save(section, count[0] + "", i);
			count[0]++;
		});
	}
	
	@Override
	public void load(ConfigurationSection section) {
		clear();
		section.getKeys(false).forEach(k -> {
			add(mapper.load(section, k));
		});
	}
	
}
