package mapper.pages;

import java.util.List;

import mapper.exceptions.PageException;
import persistence.querycommands.PageQueryCommand;

public class Page<T> {

	private List<T> content;
	private PageQueryCommand<T> command;
	private Long start;
	private Long size;
	private Long splitSize;

	public Page(PageQueryCommand<T> command, Long size) {
		this(command, size, 10l);
	}
	
	public Page(PageQueryCommand<T> command, Long size, Long splitSize) {
		this(command, 0l, size, splitSize);
	}
	
	public Page(PageQueryCommand<T> command, Long start, Long size, Long splitSize) {
		this.splitSize = splitSize;
		this.content = null;
		this.start = start;
		this.size = size;
		this.command = command;
	}
	
	public Page<T> next() throws PageException  {
		if (!isLoaded())
		{
			this.load();
			return this;
		}
		
		Page<T> p = new Page<T>(command, start + splitSize, size, splitSize);
		p.load();
		return p;
	}

	private void load() throws PageException {
		
		try {
			content = command.getContent(start, splitSize);
		} catch (Exception e) {
			throw new PageException(e);
		}
	}
	
	public String dump() {
		StringBuilder b = new StringBuilder();
		for (T t : content) {
			b.append(t.toString());
			b.append(System.lineSeparator());
		}
		return b.toString();
	}
	
	public List<T> getContent() {
		return content;
	}
	
	public Boolean hasNext() {
		return !isLoaded() || (start + splitSize < size) ;
	}

	public Long getCurrentCount() {
		return start;
	}
	
	public Long getTotalCount() {
		return size;
	}
	
	public Boolean isLoaded() {
		return content != null;
	}

	public Long getPageSize() {
		return content == null ? splitSize : content.size();
	}

}
