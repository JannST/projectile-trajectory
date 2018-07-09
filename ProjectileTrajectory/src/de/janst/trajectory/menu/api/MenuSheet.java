package de.janst.trajectory.menu.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import de.janst.trajectory.TrajectorySimulator;

public abstract class MenuSheet {

	private static int idCount;
	private static Map<Integer, MenuSheet> sheets = new HashMap<Integer, MenuSheet>();
	private static LinkedList<Integer> quedStop = new LinkedList<Integer>();
	private final int tid;
	private Inventory inventory;
	private InventoryView view;
	public ItemStack[] contents;
	private String title;
	private int size;
	private MenuSheet parent;
	private Map<String, SlotListener> listeners = new HashMap<String, SlotListener>();
	private boolean hasChildMenu;
	private Player player;
	private JavaPlugin plugin;
	
//	public Map<String, MenuSheet> menus = new HashMap<String, MenuSheet>();
	
	private MenuSheet(JavaPlugin plugin) {
		super();
		this.tid = idCount++;
		this.plugin = plugin;
	}
	
	public MenuSheet(JavaPlugin plugin, String title, int size, MenuSheet parent) {
		this(plugin, title, size, parent.getPlayer());
		this.parent = parent;
		this.parent.setChildMenu(true);
	}
	
	public MenuSheet(JavaPlugin plugin, String title, int size, Player player) {
		this(plugin);
		sheets.put(this.tid, this);
		this.player = player;
		setTitle(title);
		this.size = size;
		this.contents = new ItemStack[size];
		createInventory(getPlugin());
	}

	public abstract void initContents();
	
	public static void closeAllMenuSheets() {
		for(int i : new ArrayList<Integer>(sheets.keySet())) {
			if(sheets.containsKey(i)) {
				sheets.get(i).shutMenuConstruct();
				sheets.remove(i);
			}
		}
	}
	
	protected void setChildMenu(boolean bool) {
		this.hasChildMenu = bool;
	}
	
	public boolean hasChildMenu() {
		return this.hasChildMenu;
	}
	
	public boolean hasParent() {
		return parent != null;
	}
	
	
	public void shutMenuConstruct() {
		shutMenu();
		if(hasParent())
		this.parent.shutMenuConstruct();
	}
	
	public void shutMenu() {
		if(isActive()) {
			shutViewSave();
		}
		sheets.remove(getID());
		//Thread.dumpStack();
	}
	
	public void shutViewSave() {
		if(isActive()) {
			addToStopList();
			this.getView().close();
		}
	}
	
	public void setView(InventoryView view) {
		this.view = view;
	}
	
	public boolean isActive() {
		return this.getView() != null;
	}

	public boolean hasListeners() {
		return listeners.size() != 0;
	}
		
	public void registerListener(String name, SlotListener listener) {
		listeners.put(name, listener);
	}

	public SlotListener getListener(String name) {
		return listeners.get(name);
	}
	
	public Collection<SlotListener> getListeners() {
		return this.listeners.values();
	}
	
	public void unregisterListener(String name) {
		if(this.listeners.containsKey(name)) {
			this.listeners.remove(name);
		}
	}
	
	public int getID() {
		return this.tid;
	}

	public void setSize(int size) {
		this.size = size;
		ItemStack[] nc = new ItemStack[size];
		for(int i = 0; i < nc.length ; i++) {
			if(i >= this.contents.length-1)
				continue;
			nc[i] = contents[i];
		}
		this.contents = nc;
	}
	
	public void setTitle(String title) {
		if(title.length() > 32)
		this.title = title.substring(0, 31);
		else
		this.title = title;
	}

	
	public void updateInventory() {
		if(inventory != null) {
			if(this.inventory.getTitle().equals(this.title) && this.inventory.getSize() == this.size) {
				if(!this.inventory.getContents().equals(this.contents)) {
					this.inventory.setContents(this.contents);
				}
			}
			else
				createInventory(getPlugin());
		}
		else
		createInventory(getPlugin());
	}
	
	public Inventory createInventory(JavaPlugin plugin) {
		Inventory inv = TrajectorySimulator.getInstance().getServer().createInventory(null, size, title);
		inv.setContents(this.contents);
		this.inventory = inv;
		return inv;
	}
	
	public Inventory getInventory() {
		return this.inventory;
	}
	
	public ItemStack[] getContents() {
		return contents;
	}

	public void setContent(int insertion, ItemStack content) {
		this.contents[insertion] = content;
	}
	
	
	public void show() {
		if(this.inventory == null) {
			createInventory(getPlugin());
		}
		this.setView(this.player.openInventory(this.inventory));
	}
	
	public Player getPlayer() {
		return this.player;
	}

	
	public static Map<Integer, MenuSheet> getSheets() {
		return sheets;
	}
	
	public static MenuSheet getSheet(int id) {
		return sheets.get(id);
	}

	public InventoryView getView() {
		return view;
	}

	public int getTid() {
		return tid;
	}

	public String getTitle() {
		return title;
	}

	public int getSize() {
		return size;
	}

	public MenuSheet getParent() {
		return parent;
	}
	
	public boolean isOnStopList() {
		return quedStop.contains(getID());
	}
	
	public void removeFromStopList() {
		if(quedStop.contains(getID())) {
			quedStop.remove(new Integer(getID()));
		}
	}
	
	public void addToStopList() {
		quedStop.add(getID());
	}
	
	public void standby() {
		if(isActive()) {
			shutViewSave();
		}
	}

	public JavaPlugin getPlugin() {
		return plugin;
	}

//	public MenuSheet getMenu(String name) {
//		if(menus.containsKey(name))
//			return menus.get(name);
//		else if(parent != null && parent.menus.containsKey(name)) {
//			return parent.menus.get(name);
//		}
//		return null;
//	}
}
