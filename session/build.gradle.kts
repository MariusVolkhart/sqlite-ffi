import io.github.krakowski.jextract.JextractTask

plugins {
	`java-library`
	`sqlite-extract`
}

tasks.withType<JextractTask> {
	val headerLocation = ext[com.volkhart.gradle.SqliteExtractPlugin.SQLITE_HEADER].toString()
	header(headerLocation) {
		targetPackage.set("com.volkhart.sqlite.session")
		className.set("SQLite3Session")

		structs.add("sqlite3_session")
		functions.addAll(
			// Constructors
			"sqlite3session_create",

			// Destructors
			"sqlite3session_delete",

			// Methods
			"sqlite3session_attach",
			"sqlite3session_changeset",
			"sqlite3session_changeset_size",
			"sqlite3session_diff",
			"sqlite3session_enable",
			"sqlite3session_indirect",
			"sqlite3session_patchset",
			"sqlite3session_table_filter",
		)

		structs.add("sqlite3_changegroup")
		functions.addAll(
			// Constructors
			"sqlite3changegroup_new",

			// Destructors
			"sqlite3changegroup_delete",

			// Methods
			"sqlite3changegroup_add",
			"sqlite3changegroup_output",
		)

		structs.add("sqlite3_changeset_iter")
		functions.addAll(
			// Constructors
			"sqlite3changeset_start",
			"sqlite3changeset_start_v2",

			// Methods
			"sqlite3changeset_conflict",
			"sqlite3changeset_finalize",
			"sqlite3changeset_fk_conflicts",
			"sqlite3changeset_new",
			"sqlite3changeset_next",
			"sqlite3changeset_old",
			"sqlite3changeset_op",
			"sqlite3changeset_pk",
		)

		definedMacros.addAll(
			"SQLITE_CHANGESETAPPLY_INVERT",
			"SQLITE_CHANGESETAPPLY_NOSAVEPOINT",
			"SQLITE_CHANGESETSTART_INVERT",
			"SQLITE_CHANGESET_ABORT",
			"SQLITE_CHANGESET_CONFLICT",
			"SQLITE_CHANGESET_CONSTRAINT",
			"SQLITE_CHANGESET_DATA",
			"SQLITE_CHANGESET_FOREIGN_KEY",
			"SQLITE_CHANGESET_NOTFOUND",
			"SQLITE_CHANGESET_OMIT",
			"SQLITE_CHANGESET_REPLACE",
			"SQLITE_SESSION_CONFIG_STRMSIZE",
		)
	}
}
