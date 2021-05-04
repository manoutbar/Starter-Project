package acme.features.manager.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.entities.tasks.Task;
import acme.framework.components.Errors;
import acme.framework.components.Model;
import acme.framework.components.Request;
import acme.framework.entities.Manager;
import acme.framework.services.AbstractCreateService;
import acme.util.Utils;

@Service
public class ManagerTaskCreateService implements AbstractCreateService<Manager, Task> {

	// Internal state ---------------------------------------------------------

	@Autowired
	protected ManagerTaskRepository repository;
	
	@Autowired
	protected Utils utils;
	

	// AbstractCreateService<Employer, Job> interface -------------------------


	@Override
	public boolean authorise(final Request<Task> request) {
		assert request != null;

		return true;
	}

	@Override
	public void validate(final Request<Task> request, final Task entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;
		
		/*if (!errors.hasErrors("title")) {
			final String spam = this.utils.spamControl(entity.getTitle(), "MARKED_AS_SPAM");
			errors.state(request, spam.equals("MARKED_AS_SPAM"), "title", "master.form.error.marked-as-spam");
		}
		
		if (!errors.hasErrors("description")) {
			final String spam = this.utils.spamControl(entity.getTitle(), "MARKED_AS_SPAM");
			errors.state(request, spam.equals("MARKED_AS_SPAM"), "description", "master.form.error.marked-as-spam");
		}
		
		if (!errors.hasErrors("executionStart")) {
			final Date minimumExecutionStart = new Date();
			errors.state(request, entity.getExecutionStart().before(minimumExecutionStart), "executionStart", "manager.task.form.error.execution-start-past");
		}
		
		if (!errors.hasErrors("executionEnd")) {
			errors.state(request, entity.getExecutionEnd().before(entity.getExecutionStart()), "executionEnd", "manager.task.form.error.execution-end-before-start");
		}*/
		
		/*if (!errors.hasErrors("deadline")) {
			Calendar calendar;
			Date minimumDeadline;
						
			calendar = new GregorianCalendar();
			calendar.add(Calendar.DAY_OF_MONTH, 7);
			minimumDeadline = calendar.getTime();
			errors.state(request, entity.getDeadline().after(minimumDeadline), "deadline", "employer.job.form.error.too-close");
		}
		
		if (!errors.hasErrors("reference")) {
			Job existing;
			
			existing = this.repository.findOneJobByReference(entity.getReference());
			errors.state(request, existing == null, "reference", "employer.job.form.error.duplicated");
		}*/
	}

	@Override
	public void bind(final Request<Task> request, final Task entity, final Errors errors) {
		assert request != null;
		assert entity != null;
		assert errors != null;

		request.bind(entity, errors);
	}

	@Override
	public void unbind(final Request<Task> request, final Task entity, final Model model) {
		assert request != null;
		assert entity != null;
		assert model != null;

		request.unbind(entity, model, "title", "isPublic", "executionStart", "executionEnd", "workload", "description", "link");
	}

	@Override
	public Task instantiate(final Request<Task> request) {
		assert request != null;

		Task result;
		Manager owner;

		owner = this.repository.findOneManagerById(request.getPrincipal().getActiveRoleId());
		result = new Task();
		result.setOwner(owner);

		return result;
	}

	@Override
	public void create(final Request<Task> request, final Task entity) {
		assert request != null;
		assert entity != null;

		this.repository.save(entity);
	}

}
