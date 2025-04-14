import { process } from "sdk/bpm";
import { workspace as workspaceManager } from "sdk/platform";
import { MigrationTask } from "./task.mjs";

export class ListWorkspacesTask extends MigrationTask {
    execution = process.getExecutionContext();

    constructor() {
        super("WORKSPACES_LISTING", "WORKSPACES_LISTED", "WORKSPACES_LISTING_FAILED");
    }

    run() {
        const workspaces = workspaceManager.getWorkspacesNames();
        process.setVariable(this.execution.getId(), "workspaces", JSON.stringify(workspaces));
        process.setVariable(this.execution.getId(), "migrationState", "WORKSPACES_LISTED");
    }
}
